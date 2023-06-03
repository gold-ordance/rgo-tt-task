package rgo.tt.main.persistence.storage.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public final class PersistenceUtils {

    private PersistenceUtils() {
    }

    public static void truncateTables(DataSource ds) {
        DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource("h2/truncate.sql")), ds);
    }
}
