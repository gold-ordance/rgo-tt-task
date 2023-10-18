package rgo.tt.task.service.tasksboard;

import rgo.tt.common.utils.HelperUtils;
import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static rgo.tt.common.utils.HelperUtils.SPACE;

public class ShortenedNameTasksBoardServiceDecorator implements TasksBoardService {

    private final TasksBoardService delegate;

    public ShortenedNameTasksBoardServiceDecorator(TasksBoardService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<TasksBoard> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        return delegate.findByEntityId(entityId);
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        String shortName = Arrays.stream(board.getName().split(SPACE))
                .map(HelperUtils::getFirstSymbol)
                .collect(Collectors.joining())
                .toUpperCase(Locale.ENGLISH);

        board.setShortName(shortName);
        return delegate.save(board);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return delegate.deleteByEntityId(entityId);
    }
}
