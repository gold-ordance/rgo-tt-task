package rgo.tt.task.persistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.retry.DbRetryManager;
import rgo.tt.common.persistence.sqlstatement.retry.RetryPolicyProperties;
import rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionHandler;
import rgo.tt.common.persistence.translator.UniqueViolationPostgresH2ExceptionHandler;
import rgo.tt.task.persistence.storage.repository.task.PostgresTaskRepository;
import rgo.tt.task.persistence.storage.repository.task.RetryableTaskRepositoryDecorator;
import rgo.tt.task.persistence.storage.repository.task.TaskRepository;
import rgo.tt.task.persistence.storage.repository.task.TxTaskRepositoryDecorator;
import rgo.tt.task.persistence.storage.repository.tasksboard.PostgresTasksBoardRepository;
import rgo.tt.task.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.task.persistence.storage.repository.tasksboard.TxTasksBoardRepositoryDecorator;
import rgo.tt.task.persistence.storage.repository.taskstatus.PostgresTaskStatusRepository;
import rgo.tt.task.persistence.storage.repository.taskstatus.TaskStatusRepository;
import rgo.tt.task.persistence.storage.repository.taskstatus.TxTaskStatusRepositoryDecorator;
import rgo.tt.task.persistence.storage.repository.tasktype.PostgresTaskTypeRepository;
import rgo.tt.task.persistence.storage.repository.tasktype.TaskTypeRepository;
import rgo.tt.task.persistence.storage.repository.tasktype.TxTaskTypeRepositoryDecorator;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Import(DbDialectConfig.class)
public class PersistenceConfig {

    @Autowired private DataSource dataSource;
    @Autowired private RetryPolicyProperties retryPolicyProperties;

    @Bean
    public DbTxManager txManager() {
        return new DbTxManager(dataSource);
    }

    @Bean
    public ForeignKeyPostgresH2ExceptionHandler foreignKeyHandler() {
        return new ForeignKeyPostgresH2ExceptionHandler();
    }

    @Bean
    public UniqueViolationPostgresH2ExceptionHandler uniqueViolationHandler() {
        return new UniqueViolationPostgresH2ExceptionHandler();
    }

    @Bean
    public StatementJdbcTemplateAdapter jdbc(List<PostgresH2ExceptionHandler> handlers) {
        return new StatementJdbcTemplateAdapter(txManager(), handlers);
    }

    @Bean
    public DbRetryManager retryManager() {
        return new DbRetryManager(retryPolicyProperties);
    }

    @Bean
    public TasksBoardRepository tasksBoardRepository(StatementJdbcTemplateAdapter jdbc) {
        TasksBoardRepository repository = new PostgresTasksBoardRepository(jdbc);
        return new TxTasksBoardRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskStatusRepository taskStatusRepository(StatementJdbcTemplateAdapter jdbc) {
        TaskStatusRepository repository = new PostgresTaskStatusRepository(jdbc);
        return new TxTaskStatusRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskTypeRepository taskTypeRepository(StatementJdbcTemplateAdapter jdbc) {
        TaskTypeRepository repository = new PostgresTaskTypeRepository(jdbc);
        return new TxTaskTypeRepositoryDecorator(repository, txManager());
    }

    @Bean
    public TaskRepository taskRepository(StatementJdbcTemplateAdapter jdbc) {
        TaskRepository nativeRepository = new PostgresTaskRepository(jdbc);
        TaskRepository retryableRepository = new RetryableTaskRepositoryDecorator(nativeRepository, retryManager());
        return new TxTaskRepositoryDecorator(retryableRepository, txManager());
    }
}
