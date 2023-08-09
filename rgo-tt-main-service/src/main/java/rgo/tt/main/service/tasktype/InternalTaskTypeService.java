package rgo.tt.main.service.tasktype;

import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.repository.tasktype.TaskTypeRepository;

import java.util.List;

public class InternalTaskTypeService implements TaskTypeService {

    private final TaskTypeRepository repository;

    public InternalTaskTypeService(TaskTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskType> findAll() {
        return repository.findAll();
    }
}
