package rgo.tt.main.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import rgo.tt.main.persistence.config.properties.DbProperties;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.repository.TaskRepository;

import javax.sql.DataSource;

@Configuration
@ConfigurationPropertiesScan
public class PersistenceConfig {

    private static final String DB_NAME = "task";

    @Bean
    @Profile("dev | test")
    public DataSource h2() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(DB_NAME)
                .addScript("classpath:h2/init.sql")
                .build();
    }

    @Bean
    @Profile("!dev & !test")
    public DataSource pg(DbProperties dbProp) {
        HikariConfig hk = new HikariConfig();
        hk.setJdbcUrl(dbProp.getUrl());
        hk.setSchema(dbProp.getSchema());
        hk.setUsername(dbProp.getUsername());
        hk.setPassword(dbProp.getPassword());
        hk.setMaximumPoolSize(dbProp.getMaxPoolSize());
        return new HikariDataSource(hk);
    }

    @Bean
    public DbTxManager txManager(DataSource ds) {
        return new DbTxManager(ds);
    }

    @Bean
    public TaskRepository taskRepository(DbTxManager txManager) {
        return new TaskRepository(txManager);
    }
}
