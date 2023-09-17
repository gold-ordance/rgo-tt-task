package rgo.tt.task.service.taskstatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.task.persistence.storage.entity.TaskStatus;

import java.util.List;

public class ValidateTaskStatusServiceDecorator implements TaskStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTaskStatusServiceDecorator.class);

    private final TaskStatusService delegate;

    public ValidateTaskStatusServiceDecorator(TaskStatusService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<TaskStatus> findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }
}
