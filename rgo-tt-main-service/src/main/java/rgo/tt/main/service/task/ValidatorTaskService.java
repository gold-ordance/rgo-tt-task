package rgo.tt.main.service.task;

import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.checkObjectId;
import static rgo.tt.common.validator.ValidatorUtils.checkString;

public class ValidatorTaskService implements TaskService {

    private final TaskService delegate;

    public ValidatorTaskService(TaskService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Task> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        checkObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name) {
        checkString(name, "name");
        return delegate.findSoftlyByName(name);
    }

    @Override
    public Task save(Task task) {
        checkString(task.getName(), "name");
        return delegate.save(task);
    }

    @Override
    public Task update(Task task) {
        checkObjectId(task.getEntityId(), "entityId");
        checkString(task.getName(), "name");
        return delegate.update(task);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        checkObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
