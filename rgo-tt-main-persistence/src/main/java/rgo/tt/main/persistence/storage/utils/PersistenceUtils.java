package rgo.tt.main.persistence.storage.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public final class PersistenceUtils {

    private static final String DB_NAME = "task";

    private PersistenceUtils() {
    }

    public static DataSource h2Source() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(DB_NAME)
                .addScript("classpath:h2/init-tables.sql")
                .build();
    }

    public static void truncateTables(DataSource ds) {
        Resource resource = new ClassPathResource("h2/truncate.sql");
        DatabasePopulator populator = new ResourceDatabasePopulator(resource);
        DatabasePopulatorUtils.execute(populator, ds);
    }
}
