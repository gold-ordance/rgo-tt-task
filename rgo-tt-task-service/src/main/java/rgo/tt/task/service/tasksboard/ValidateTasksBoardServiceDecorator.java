package rgo.tt.task.service.tasksboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

public class ValidateTasksBoardServiceDecorator implements TasksBoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTasksBoardServiceDecorator.class);

    private final TasksBoardService delegate;

    public ValidateTasksBoardServiceDecorator(TasksBoardService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<TasksBoard> findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        LOGGER.info("Request 'save' received: board={}", board);
        validateString(board.getName(), "name");
        return delegate.save(board);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        LOGGER.info("Request 'deleteByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
