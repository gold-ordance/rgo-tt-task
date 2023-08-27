package rgo.tt.main.persistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionHandler;
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
import java.util.List;

@Configuration
@ConfigurationPropertiesScan
@Import(DataSourceConfig.class)
public class PersistenceConfig {

    @Autowired private DataSource dataSource;

    @Bean
    public DbTxManager txManager() {
        return new DbTxManager(dataSource);
    }

    @Bean
    public ForeignKeyPostgresH2ExceptionHandler foreignKeyHandler() {
        return new ForeignKeyPostgresH2ExceptionHandler();
    }

    @Bean
    public StatementJdbcTemplateAdapter jdbc(List<PostgresH2ExceptionHandler> handlers) {
        return new StatementJdbcTemplateAdapter(txManager(), handlers);
    }

    @Bean
    public TasksBoardRepository tasksBoardRepository(StatementJdbcTemplateAdapter jdbc) {
        TasksBoardRepository repository = new PostgresTasksBoardRepository(jdbc);
        return new TxTasksBoardRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskStatusRepository taskStatusRepository(StatementJdbcTemplateAdapter jdbc) {
        PostgresTaskStatusRepository repository = new PostgresTaskStatusRepository(jdbc);
        return new TxTaskStatusRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskTypeRepository taskTypeRepository(StatementJdbcTemplateAdapter jdbc) {
        PostgresTaskTypeRepository repository = new PostgresTaskTypeRepository(jdbc);
        return new TxTaskTypeRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskRepository taskRepository(StatementJdbcTemplateAdapter jdbc) {
        PostgresTaskRepository repository = new PostgresTaskRepository(jdbc);
        return new TxTaskRepositoryDecorator(repository, txManager());
    }
}
