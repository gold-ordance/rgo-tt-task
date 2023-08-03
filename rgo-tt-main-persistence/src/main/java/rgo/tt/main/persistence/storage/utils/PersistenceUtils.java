package rgo.tt.main.persistence.storage.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import rgo.tt.main.persistence.config.properties.DbProperties;

import javax.sql.DataSource;

public final class PersistenceUtils {

    private static final String DB_NAME = "task";

    private PersistenceUtils() {
    }

    public static DataSource hikariSource(DbProperties dbProp) {
        HikariConfig hk = new HikariConfig();
        hk.setJdbcUrl(dbProp.getUrl());
        hk.setSchema(dbProp.getSchema());
        hk.setUsername(dbProp.getUsername());
        hk.setPassword(dbProp.getPassword());
        hk.setMaximumPoolSize(dbProp.getMaxPoolSize());
        hk.setAutoCommit(false);
        return new HikariDataSource(hk);
    }

    public static DataSource h2Source() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(DB_NAME)
                .addScript("classpath:h2/init.sql")
                .build();
    }

    public static void truncateTables(DataSource ds) {
        DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource("h2/truncate.sql")), ds);
    }
}
