package rgo.tt.task.rest.api.tasksboard;

import org.junit.jupiter.api.Test;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
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

        List<String> nonEmptyFields = List.of("name");
        validateNullFieldsExcept(board, nonEmptyFields);
    }

    @Test
    void map_entityId() {
        long randomEntityId = randomPositiveLong();
        TasksBoard board = TasksBoardMapper.map(randomEntityId);

        assertThat(board.getEntityId()).isEqualTo(randomEntityId);
    }

    private void compare(TasksBoardDto dto, TasksBoard board) {
        assertThat(dto.getEntityId()).isEqualTo(board.getEntityId());
        assertThat(dto.getName()).isEqualTo(board.getName());
        assertThat(dto.getShortName()).isEqualTo(board.getShortName());
    }
}
