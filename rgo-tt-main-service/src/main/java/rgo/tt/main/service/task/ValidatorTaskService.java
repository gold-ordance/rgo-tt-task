package rgo.tt.main.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.checkObjectId;
import static rgo.tt.common.validator.ValidatorUtils.checkString;

public class ValidatorTaskService implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorTaskService.class);

    private final TaskService delegate;

    public ValidatorTaskService(TaskService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Task> findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        checkObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name) {
        LOGGER.info("Request 'findSoftlyByName' received: name={}", name);
        checkString(name, "name");
        return delegate.findSoftlyByName(name);
    }

    @Override
    public Task save(Task task) {
        LOGGER.info("Request 'save' received: task={}", task);
        checkString(task.getName(), "name");
        return delegate.save(task);
    }

    @Override
    public Task update(Task task) {
        LOGGER.info("Request 'update' received: task={}", task);
        checkObjectId(task.getEntityId(), "entityId");
        checkObjectId(task.getStatus().getEntityId(), "statusId");
        checkString(task.getName(), "name");
        return delegate.update(task);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        LOGGER.info("Request 'deleteByEntityId' received: entityId={}", entityId);
        checkObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
