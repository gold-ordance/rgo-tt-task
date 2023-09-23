package rgo.tt.task.boot.healthcheck;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public class ProbeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProbeService.class);

    private volatile boolean ready;

    static HttpResponse ok() {
        return HttpResponse.of(HttpStatus.OK, MediaType.HTML_UTF_8, "{healthy: true}");
    }

    static HttpResponse failure() {
        return HttpResponse.of(HttpStatus.BAD_REQUEST, MediaType.HTML_UTF_8, "Not ready");
    }

    @Get("/liveness")
    public HttpResponse livenessProbe() {
        return ok();
    }

    @Get("/readiness")
    public HttpResponse readinessProbe() {
        return ready ? ok() : failure();
    }

    @EventListener
    public void start(ApplicationReadyEvent event) {
        ready = true;
        LOGGER.info("The application is ready!");
    }
}
