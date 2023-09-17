package rgo.tt.task.service.tasktype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.task.persistence.storage.entity.TaskType;

import java.util.List;

public class ValidateTaskTypeServiceDecorator implements TaskTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTaskTypeServiceDecorator.class);

    private final TaskTypeService delegate;

    public ValidateTaskTypeServiceDecorator(TaskTypeService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<TaskType> findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }
}
