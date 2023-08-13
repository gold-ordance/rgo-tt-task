package rgo.tt.main.service.tasksboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.checkObjectId;
import static rgo.tt.common.validator.ValidatorUtils.checkString;

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
        checkObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        LOGGER.info("Request 'save' received: board={}", board);
        checkString(board.getName(), "name");
        return delegate.save(board);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        LOGGER.info("Request 'deleteByEntityId' received: entityId={}", entityId);
        checkObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
