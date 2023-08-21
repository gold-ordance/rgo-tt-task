package rgo.tt.main.persistence.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.main.persistence.config.properties.DbProperties;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.repository.task.PostgresTaskRepository;
import rgo.tt.main.persistence.storage.repository.task.TaskRepository;
import rgo.tt.main.persistence.storage.repository.task.TxTaskRepositoryDecorator;
import rgo.tt.main.persistence.storage.repository.tasksboard.PostgresTasksBoardRepository;
import rgo.tt.main.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.main.persistence.storage.repository.tasksboard.TxTasksBoardRepositoryDecorator;
import rgo.tt.main.persistence.storage.repository.taskstatus.PostgresTaskStatusRepository;
import rgo.tt.main.persistence.storage.repository.taskstatus.TaskStatusRepository;
import rgo.tt.main.persistence.storage.repository.taskstatus.TxTaskStatusRepositoryDecorator;
import rgo.tt.main.persistence.storage.repository.tasktype.PostgresTaskTypeRepository;
import rgo.tt.main.persistence.storage.repository.tasktype.TaskTypeRepository;
import rgo.tt.main.persistence.storage.repository.tasktype.TxTaskTypeRepositoryDecorator;

import javax.sql.DataSource;

import static rgo.tt.main.persistence.storage.utils.PersistenceUtils.h2Source;
import static rgo.tt.main.persistence.storage.utils.PersistenceUtils.hikariSource;

@Configuration
@ConfigurationPropertiesScan
public class PersistenceConfig {

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "dialect", havingValue = "H2", matchIfMissing = true)
    public DataSource h2() {
        return h2Source();
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "dialect", havingValue = "POSTGRES")
    public DataSource pg(DbProperties dbProp) {
        return hikariSource(dbProp);
    }

    @Bean
    public DbTxManager txManager(DataSource ds) {
        return new DbTxManager(ds);
    }

    @Bean
    public TasksBoardRepository tasksBoardRepository(DbTxManager txManager) {
        return new TxTasksBoardRepositoryDecorator(
                new PostgresTasksBoardRepository(txManager), txManager);
    }

    @Bean
    public TaskStatusRepository taskStatusRepository(DbTxManager txManager) {
        return new TxTaskStatusRepositoryDecorator(
                new PostgresTaskStatusRepository(txManager), txManager);
    }

    @Bean
    public TaskTypeRepository taskTypeRepository(DbTxManager txManager) {
        return new TxTaskTypeRepositoryDecorator(
                new PostgresTaskTypeRepository(txManager), txManager);
    }

    @Bean
    public TaskRepository taskRepository(DbTxManager txManager) {
        return new TxTaskRepositoryDecorator(
                new PostgresTaskRepository(txManager), txManager);
    }
}
