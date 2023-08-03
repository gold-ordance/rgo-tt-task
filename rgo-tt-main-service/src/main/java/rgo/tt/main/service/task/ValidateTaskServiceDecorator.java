package rgo.tt.main.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.checkObjectId;
import static rgo.tt.common.validator.ValidatorUtils.checkString;

public class ValidateTaskServiceDecorator implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTaskServiceDecorator.class);

    private final TaskService delegate;

    public ValidateTaskServiceDecorator(TaskService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Task> findAll(Long boardId) {
        LOGGER.info("Request 'findAll' received: boardId={}", boardId);
        checkObjectId(boardId, "boardId");
        return delegate.findAll(boardId);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        checkObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        LOGGER.info("Request 'findSoftlyByName' received: name={}, boardId={}", name, boardId);
        checkString(name, "name");
        checkObjectId(boardId, "boardId");
        return delegate.findSoftlyByName(name, boardId);
    }

    @Override
    public Task save(Task task) {
        LOGGER.info("Request 'save' received: task={}", task);
        checkString(task.getName(), "name");
        checkObjectId(task.getBoard().getEntityId(), "boardId");
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
