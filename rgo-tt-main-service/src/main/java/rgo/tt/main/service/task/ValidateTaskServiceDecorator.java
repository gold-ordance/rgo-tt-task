package rgo.tt.main.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

public class ValidateTaskServiceDecorator implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTaskServiceDecorator.class);

    private final TaskService delegate;

    public ValidateTaskServiceDecorator(TaskService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        LOGGER.info("Request 'findAllForBoard' received: boardId={}", boardId);
        validateObjectId(boardId, "boardId");
        return delegate.findAllForBoard(boardId);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        LOGGER.info("Request 'findSoftlyByName' received: name={}, boardId={}", name, boardId);
        validateString(name, "name");
        validateObjectId(boardId, "boardId");
        return delegate.findSoftlyByName(name, boardId);
    }

    @Override
    public Task save(Task task) {
        LOGGER.info("Request 'save' received: task={}", task);
        validateString(task.getName(), "name");
        validateObjectId(task.getBoard().getEntityId(), "boardId");
        validateObjectId(task.getType().getEntityId(), "typeId");
        return delegate.save(task);
    }

    @Override
    public Task update(Task task) {
        LOGGER.info("Request 'update' received: task={}", task);
        validateObjectId(task.getEntityId(), "entityId");
        validateString(task.getName(), "name");
        validateObjectId(task.getType().getEntityId(), "typeId");
        validateObjectId(task.getStatus().getEntityId(), "statusId");
        return delegate.update(task);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        LOGGER.info("Request 'deleteByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
