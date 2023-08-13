package rgo.tt.main.rest.api.task;

import org.junit.jupiter.api.Test;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.main.rest.api.tasksboard.TasksBoardMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rgo.tt.common.utils.TestUtils.assertNullFields;
import static rgo.tt.main.rest.api.RequestGenerator.createTasksBoardSaveRequest;

class TasksBoardMapperTest {

    @Test
    void map_saveRequest() throws IllegalAccessException {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        TasksBoard board = TasksBoardMapper.map(rq);

        assertEquals(rq.getName(), board.getName());

        List<String> nonEmptyFields = List.of("name", "board");
        assertNullFields(board, nonEmptyFields);
    }
}
