package rgo.tt.task.rest.api.tasksboard;

import org.junit.jupiter.api.Test;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.HelperUtils.getFirstSymbol;
import static rgo.tt.common.utils.TestUtils.validateNullFieldsExcept;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;
import static rgo.tt.task.rest.api.RequestGenerator.createTasksBoardSaveRequest;

class TasksBoardMapperTest {

    @Test
    void map_toTasksBoardDto() {
        TasksBoard tasksBoard = randomTasksBoard();
        TasksBoardDto dto = TasksBoardMapper.map(tasksBoard);
        compare(dto, tasksBoard);
    }

    @Test
    void map_toTasksBoardDtoList() {
        List<TasksBoard> boards = List.of(randomTasksBoard());
        List<TasksBoardDto> dtoList = TasksBoardMapper.map(boards);

        assertThat(boards).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), boards.get(0));
    }

    @Test
    void map_saveRequest() {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        TasksBoard board = TasksBoardMapper.map(rq);

        assertThat(board.getName()).isEqualTo(rq.getName());

        List<String> nonEmptyFields = List.of("name", "shortName");
        validateNullFieldsExcept(board, nonEmptyFields);
        assertThat(board.getShortName()).isEqualTo(getFirstSymbol(board.getName()));
    }

    @Test
    void shortName_null() {
        assertThat(TasksBoardMapper.shortName(null)).isNull();
    }

    @Test
    void shortName() {
        String string = "Task tracker";
        String shortString = "TT";

        String actual = TasksBoardMapper.shortName(string);
        assertThat(actual).isEqualTo(shortString);
    }

    private void compare(TasksBoardDto dto, TasksBoard board) {
        assertThat(dto.getEntityId()).isEqualTo(board.getEntityId());
        assertThat(dto.getName()).isEqualTo(board.getName());
        assertThat(dto.getShortName()).isEqualTo(board.getShortName());
    }
}
