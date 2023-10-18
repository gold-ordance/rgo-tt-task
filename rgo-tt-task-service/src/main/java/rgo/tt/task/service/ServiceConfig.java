package rgo.tt.task.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.task.persistence.config.PersistenceConfig;
import rgo.tt.task.persistence.storage.repository.task.TaskRepository;
import rgo.tt.task.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.task.persistence.storage.repository.taskstatus.TaskStatusRepository;
import rgo.tt.task.persistence.storage.repository.tasktype.TaskTypeRepository;
import rgo.tt.task.service.task.TaskService;
import rgo.tt.task.service.task.InternalTaskService;
import rgo.tt.task.service.tasksboard.InternalTasksBoardService;
import rgo.tt.task.service.tasksboard.ShortenedNameTasksBoardServiceDecorator;
import rgo.tt.task.service.tasksboard.TasksBoardService;
import rgo.tt.task.service.taskstatus.InternalTaskStatusService;
import rgo.tt.task.service.taskstatus.TaskStatusService;
import rgo.tt.task.service.tasktype.InternalTaskTypeService;
import rgo.tt.task.service.tasktype.TaskTypeService;

@Configuration
@Import(PersistenceConfig.class)
public class ServiceConfig {

    @Bean
    public TasksBoardService tasksBoardService(TasksBoardRepository repository) {
        return new ShortenedNameTasksBoardServiceDecorator(
                new InternalTasksBoardService(repository));
    }

    @Bean
    public TaskTypeService taskTypeService(TaskTypeRepository repository) {
        return new InternalTaskTypeService(repository);
    }

    @Bean
    public TaskStatusService taskStatusService(TaskStatusRepository repository) {
        return new InternalTaskStatusService(repository);
    }

    @Bean
    public TaskService taskService(TaskRepository repository) {
        return new InternalTaskService(repository);
    }
}
