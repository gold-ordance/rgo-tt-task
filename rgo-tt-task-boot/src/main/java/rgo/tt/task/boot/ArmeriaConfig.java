package rgo.tt.task.boot;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.task.boot.healthcheck.ProbeService;
import rgo.tt.task.rest.api.task.RestTaskService;
import rgo.tt.task.rest.api.tasksboard.RestTasksBoardService;
import rgo.tt.task.rest.api.taskstatus.RestTaskStatusService;
import rgo.tt.task.rest.api.tasktype.RestTaskTypeService;

@Configuration
public class ArmeriaConfig {

    @Autowired private RestTaskService restTaskService;
    @Autowired private RestTasksBoardService restTasksBoardService;
    @Autowired private RestTaskStatusService restTaskStatusService;
    @Autowired private RestTaskTypeService restTaskTypeService;

    @Bean
    public ProbeService probeService() {
        return new ProbeService();
    }

    @Bean
    public ArmeriaServerConfigurator armeriaConfigurator() {
        return serverBuilder ->
                serverBuilder
                        .annotatedService("/internal", probeService())
                        .annotatedService("/tasks", restTaskService)
                        .annotatedService("/tasks-boards", restTasksBoardService)
                        .annotatedService("/types", restTaskTypeService)
                        .annotatedService("/statuses", restTaskStatusService)
                        .serviceUnder("/docs", docService())
                        .decorator(LoggingService.newDecorator());
    }

    private DocService docService() {
        return DocService.builder().build();
    }
}
