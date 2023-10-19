package rgo.tt.task.service.tasksboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import rgo.tt.common.utils.HelperUtils;
import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;

@ExtendWith(MockitoExtension.class)
class ShortenedNameTasksBoardServiceDecoratorTest {

    private ShortenedNameTasksBoardServiceDecorator decorator;
    private TasksBoardService service;

    @BeforeEach
    void setUp() {
        service = mock(TasksBoardService.class);
        decorator = new ShortenedNameTasksBoardServiceDecorator(service);
    }

    @Test
    void save_convertNameToShortName() {
        TasksBoard board = randomTasksBoard();
        when(service.save(board)).thenReturn(board);

        TasksBoard saved = decorator.save(board);
        assertThat(saved.getShortName())
                .isEqualTo(HelperUtils.getFirstSymbol(board.getName()).toUpperCase(Locale.ENGLISH));
    }
}
