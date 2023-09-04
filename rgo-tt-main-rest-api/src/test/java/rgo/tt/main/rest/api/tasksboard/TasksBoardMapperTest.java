package rgo.tt.main.rest.api.tasksboard;

import org.junit.jupiter.api.Test;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rgo.tt.common.utils.HelperUtils.getFirstSymbol;
import static rgo.tt.common.utils.TestUtils.validateNullFieldsExcept;
import static rgo.tt.main.rest.api.RequestGenerator.createTasksBoardSaveRequest;

class TasksBoardMapperTest {

    @Test
    void map_saveRequest() {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        TasksBoard board = TasksBoardMapper.map(rq);

        assertEquals(rq.getName(), board.getName());

        List<String> nonEmptyFields = List.of("name", "shortName");
        validateNullFieldsExcept(board, nonEmptyFields);
        assertTrue(getFirstSymbol(board.getName()).equalsIgnoreCase(board.getShortName()));
    }

    @Test
    void shortName_null() {
        assertNull(TasksBoardMapper.shortName(null));
    }

    @Test
    void shortName() {
        String string = "Task tracker";
        String shortString = "TT";

        String actual = TasksBoardMapper.shortName(string);
        assertEquals(shortString, actual);
    }
}
