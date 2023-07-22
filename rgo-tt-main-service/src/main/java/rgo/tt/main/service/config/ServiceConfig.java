package rgo.tt.main.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.repository.TaskRepository;
import rgo.tt.main.persistence.storage.repository.TaskStatusRepository;
import rgo.tt.main.service.task.TaskService;
import rgo.tt.main.service.task.InternalTaskService;
import rgo.tt.main.service.task.ValidatorTaskService;
import rgo.tt.main.service.taskstatus.InternalTaskStatusService;
import rgo.tt.main.service.taskstatus.TaskStatusService;
import rgo.tt.main.service.taskstatus.ValidatorTaskStatusService;

@Configuration
@Import(PersistenceConfig.class)
public class ServiceConfig {

    @Bean
    public TaskService taskService(TaskRepository repository) {
        return new ValidatorTaskService(
                new InternalTaskService(repository));
    }

    @Bean
    public TaskStatusService taskStatusService(TaskStatusRepository repository) {
        return new ValidatorTaskStatusService(
                new InternalTaskStatusService(repository));
    }
}
