package rgo.tt.main.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.repository.task.TaskRepository;
import rgo.tt.main.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.main.persistence.storage.repository.taskstatus.TaskStatusRepository;
import rgo.tt.main.persistence.storage.repository.tasktype.TaskTypeRepository;
import rgo.tt.main.service.task.TaskService;
import rgo.tt.main.service.task.InternalTaskService;
import rgo.tt.main.service.task.ValidateTaskServiceDecorator;
import rgo.tt.main.service.tasksboard.InternalTasksBoardService;
import rgo.tt.main.service.tasksboard.TasksBoardService;
import rgo.tt.main.service.tasksboard.ValidateTasksBoardServiceDecorator;
import rgo.tt.main.service.taskstatus.InternalTaskStatusService;
import rgo.tt.main.service.taskstatus.TaskStatusService;
import rgo.tt.main.service.taskstatus.ValidateTaskStatusServiceDecorator;
import rgo.tt.main.service.tasktype.InternalTaskTypeService;
import rgo.tt.main.service.tasktype.TaskTypeService;
import rgo.tt.main.service.tasktype.ValidateTaskTypeServiceDecorator;

@Configuration
@Import(PersistenceConfig.class)
public class ServiceConfig {

    @Bean
    public TasksBoardService tasksBoardService(TasksBoardRepository repository) {
        return new ValidateTasksBoardServiceDecorator(
                new InternalTasksBoardService(repository));
    }

    @Bean
    public TaskTypeService taskTypeService(TaskTypeRepository repository) {
        return new ValidateTaskTypeServiceDecorator(
                new InternalTaskTypeService(repository));
    }

    @Bean
    public TaskStatusService taskStatusService(TaskStatusRepository repository) {
        return new ValidateTaskStatusServiceDecorator(
                new InternalTaskStatusService(repository));
    }

    @Bean
    public TaskService taskService(TaskRepository repository) {
        return new ValidateTaskServiceDecorator(
                new InternalTaskService(repository));
    }
}
