package rgo.tt.main.persistence.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;
import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

public class DbTxManager extends AbstractDataSource implements SmartDataSource {

    private static final Logger log = LoggerFactory.getLogger(DbTxManager.class);

    private final DataSource dataSource;
    private final StatementJdbcTemplateAdapter jdbc;
    private final ThreadLocal<ConnectionHolder> txConnection = new ThreadLocal<>();

    public DbTxManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbc = new StatementJdbcTemplateAdapter(new NamedParameterJdbcTemplate(this));
    }

    public StatementJdbcTemplateAdapter jdbc() {
        return jdbc;
    }

    public void tx(Runnable runnable) {
        runAtomically(() -> {
            runnable.run();
            return null;
        });
    }

    public <T> T tx(Supplier<T> supplier) {
        return runAtomically(supplier);
    }

    private <T> T runAtomically(Supplier<T> supplier) {
        if (hasConnection()) {
            return supplier.get();
        }

        acquireConnection();

        try {
            T result = supplier.get();
            commit();
            return result;
        } catch (Exception exp) {
            silentRollback().ifPresent(e -> log.warn("Tx rollback failed.", e));
            if (exp instanceof BaseException) throw (BaseException) exp;
            throw new BaseException("Tx failed.", exp);
        } finally {
            silentReleaseConnection().ifPresent(e -> log.warn("Connection release failed.", e));
        }
    }

    private void commit() throws SQLException {
        log.trace("Commit tx.");
        ConnectionHolder connectionHolder = txConnection.get();

        if (connectionHolder.connection != null) {
            connectionHolder.connection.commit();
        }
    }

    private boolean hasConnection() {
        return txConnection.get() != null;
    }

    private void acquireConnection() {
        log.trace("Start tx.");
        txConnection.set(new ConnectionHolder());
    }

    private Optional<Exception> silentRollback() {
        log.trace("Rollback tx.");
        ConnectionHolder connectionHolder = txConnection.get();

        if (connectionHolder.connection != null) {
            try {
                connectionHolder.connection.rollback();
            } catch (Exception th) {
                return Optional.of(th);
            }
        }

        return Optional.empty();
    }

    private Optional<Exception> silentReleaseConnection() {
        ConnectionHolder connectionHolder = txConnection.get();

        if (connectionHolder.connection != null) {
            try {
                connectionHolder.connection.close();
            } catch (Exception th) {
                return Optional.of(th);
            } finally {
                txConnection.remove();
            }
        }

        return Optional.empty();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (hasConnection()) {
            return txConnection.get().get();
        }
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public boolean shouldClose(Connection con) {
        if (!hasConnection()) return true;
        return txConnection.get().connection != con;
    }

    private class ConnectionHolder {

        private Connection connection;

        Connection get() throws SQLException {
            if (connection == null) {
                connection = dataSource.getConnection();
                log.trace("Acquire connection={}", connection);
                connection.setAutoCommit(false);
            }
            return connection;
        }

        @Override
        public String toString() {
            return "ConnectionHolder{connection=" + connection + '}';
        }
    }
}