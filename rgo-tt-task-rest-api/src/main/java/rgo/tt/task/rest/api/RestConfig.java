package rgo.tt.task.rest.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.task.rest.api.task.InternalRestTaskService;
import rgo.tt.task.rest.api.task.RestTaskService;
import rgo.tt.task.rest.api.task.ValidateRestTaskServiceDecorator;
import rgo.tt.task.rest.api.tasksboard.InternalRestTasksBoardService;
import rgo.tt.task.rest.api.tasksboard.RestTasksBoardService;
import rgo.tt.task.rest.api.tasksboard.ValidateRestTasksBoardServiceDecorator;
import rgo.tt.task.rest.api.taskstatus.InternalRestTaskStatusService;
import rgo.tt.task.rest.api.taskstatus.RestTaskStatusService;
import rgo.tt.task.rest.api.taskstatus.ValidateRestTaskStatusServiceDecorator;
import rgo.tt.task.rest.api.tasktype.InternalRestTaskTypeService;
import rgo.tt.task.rest.api.tasktype.RestTaskTypeService;
import rgo.tt.task.rest.api.tasktype.ValidateRestTaskTypeServiceDecorator;
import rgo.tt.task.service.ServiceConfig;
import rgo.tt.task.service.task.TaskService;
import rgo.tt.task.service.tasksboard.TasksBoardService;
import rgo.tt.task.service.taskstatus.TaskStatusService;
import rgo.tt.task.service.tasktype.TaskTypeService;

@Configuration
@Import(ServiceConfig.class)
public class RestConfig {

    @Bean
    public RestTaskService restClientService(TaskService service) {
        return new ValidateRestTaskServiceDecorator(
                new InternalRestTaskService(service));
    }

    @Bean
    public RestTasksBoardService restTasksBoardService(TasksBoardService service) {
        return new ValidateRestTasksBoardServiceDecorator(
                new InternalRestTasksBoardService(service));
    }

    @Bean
    public RestTaskStatusService restTaskStatusService(TaskStatusService service) {
        return new ValidateRestTaskStatusServiceDecorator(
                new InternalRestTaskStatusService(service));
    }

    @Bean
    public RestTaskTypeService restTaskTypeService(TaskTypeService service) {
        return new ValidateRestTaskTypeServiceDecorator(
                new InternalRestTaskTypeService(service));
    }
}
