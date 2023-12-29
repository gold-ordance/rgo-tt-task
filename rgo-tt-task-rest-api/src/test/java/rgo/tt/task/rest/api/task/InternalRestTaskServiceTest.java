package rgo.tt.task.rest.api.task;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.task.rest.api.RestConfig;
import rgo.tt.task.rest.api.task.dto.TaskDto;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;
import rgo.tt.task.rest.api.task.response.TaskGetEntityResponse;
import rgo.tt.task.rest.api.task.response.TaskGetListResponse;
import rgo.tt.task.rest.api.task.response.TaskModifyResponse;
import rgo.tt.task.service.task.TaskService;
import rgo.tt.task.service.tasksboard.TasksBoardService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.delete;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.get;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.post;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.put;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startArmeriaServer;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.common.armeria.rest.RestUtils.fromJson;
import static rgo.tt.common.armeria.rest.RestUtils.json;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.TO_DO;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskBuilder;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskPutRequest;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskSaveRequest;
import static rgo.tt.task.rest.api.task.TaskMapper.map;
import static rgo.tt.task.rest.api.taskstatus.TaskStatusMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class InternalRestTaskServiceTest {

    @Autowired private RestTaskService restService;
    @Autowired private TaskService service;
    @Autowired private TasksBoardService boardService;

    @BeforeEach
    void setUp() {
        startArmeriaServer(restService);
        H2PersistenceUtils.truncateTables();
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findAllForBoard_boardIdIsFake() {
        long fakeBoardId = randomPositiveLong();

        String json = get("?boardId=" + fakeBoardId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId not found in the storage.");
    }

    @Test
    void findAllForBoard_empty() {
        TasksBoard board = insertTasksBoard();

        String json = get("?boardId=" + board.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isEmpty();
    }

    @Test
    void findAllForBoard_oneBoard() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        String json = get("?boardId=" + board.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual)
                .hasSize(1)
                .contains(saved);
    }

    @Test
    void findAllForBoard_twoBoard() {
        TasksBoard board1 = insertTasksBoard();
        TaskDto saved1 = insertTask(board1);

        TasksBoard board2 = insertTasksBoard();
        insertTask(board2);

        String json = get("?boardId=" + board1.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual)
                .hasSize(1)
                .contains(saved1);
    }

    @Test
    void findByEntityId_entityIdIsFake() {
        long fakeEntityId = randomPositiveLong();

        String json = get("/" + fakeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
        assertThat(response.getStatus().getMessage()).isNull();
    }

    @Test
    void findByEntityId() {
        TasksBoard board = insertTasksBoard();
        TaskDto expected = insertTask(board);

        String json = get("/" + expected.getEntityId());
        TaskGetEntityResponse response = fromJson(json, TaskGetEntityResponse.class);
        TaskDto actual = response.getTask();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByName_nameIsFake() {
        TasksBoard board = insertTasksBoard();
        String fakeName = randomString();

        String json = get("?name=" + fakeName + "&boardId=" + board.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isEmpty();
    }

    @Test
    void findByName_boardIdIsFake() {
        String name = randomString();
        long fakeBoardId = randomPositiveLong();

        String json = get("?name=" + name + "&boardId=" + fakeBoardId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId not found in the storage.");
    }

    @Test
    void findByName_oneBoard() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        String json = get("?name=" + saved.getName() + "&boardId=" + board.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual)
                .hasSize(1)
                .contains(saved);
    }

    @Test
    void findByName_twoBoard()  {
        TasksBoard board1 = insertTasksBoard();
        TasksBoard board2 = insertTasksBoard();
        TaskDto saved = insertTask(board1);
        insertTask(board2);

        String json = get("?name=" + saved.getName() + "&boardId=" + board1.getEntityId());
        TaskGetListResponse response = fromJson(json, TaskGetListResponse.class);
        List<TaskDto> actual = response.getTasks();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual)
                .hasSize(1)
                .contains(saved);
    }

    @Test
    void save_invalidEntity_boardIdIsFake() {
        TaskSaveRequest rq = createTaskSaveRequest();

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId not found in the storage.");
    }

    @Test
    void save_invalidEntity_typeIdIsFake() {
        TasksBoard board = insertTasksBoard();

        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(board.getEntityId());
        rq.setTypeId(0L);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId not found in the storage.");
    }

    @Test
    void save_statusIsSpecified() {
        TasksBoard board = insertTasksBoard();
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(board.getEntityId());

        String json = post(json(rq));
        TaskModifyResponse response = fromJson(json, TaskModifyResponse.class);
        TaskDto actual = response.getTask();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.STORED);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual.getName()).isEqualTo(rq.getName());
        assertThat(actual.getDescription()).isEqualTo(rq.getDescription());
        assertThat(actual.getBoard().getEntityId()).isEqualTo(rq.getBoardId());
        assertThat(actual.getType().getEntityId()).isEqualTo(rq.getTypeId());
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getLastModifiedDate()).isNotNull();
        assertThat(actual.getNumber()).isNotNull();
        assertThat(actual.getStatus().getEntityId()).isEqualTo(rq.getStatusId());
    }

    @Test
    void save_statusNotSpecified() {
        TasksBoard board = insertTasksBoard();
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setStatusId(null);
        rq.setBoardId(board.getEntityId());

        String json = post(json(rq));
        TaskModifyResponse response = fromJson(json, TaskModifyResponse.class);
        TaskDto actual = response.getTask();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.STORED);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual.getName()).isEqualTo(rq.getName());
        assertThat(actual.getDescription()).isEqualTo(rq.getDescription());
        assertThat(actual.getBoard().getEntityId()).isEqualTo(rq.getBoardId());
        assertThat(actual.getType().getEntityId()).isEqualTo(rq.getTypeId());
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getLastModifiedDate()).isNotNull();
        assertThat(actual.getNumber()).isNotNull();
        assertThat(actual.getStatus()).isEqualTo(map(TO_DO));
    }

    @Test
    void put_invalidEntity_entityIdIsFake() {
        TaskPutRequest rq = createTaskPutRequest();

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId not found in the storage.");
    }

    @Test
    void put_invalidEntity_typeIdIsFake() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(saved.getEntityId());
        rq.setTypeId(0L);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId not found in the storage.");
    }

    @Test
    void put_invalidEntity_statusIdIsFake() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(saved.getEntityId());
        rq.setStatusId(0L);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isEqualTo("The statusId not found in the storage.");
    }

    @Test
    void putTask() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(saved.getEntityId());

        String json = put(json(rq));
        TaskModifyResponse response = fromJson(json, TaskModifyResponse.class);
        TaskDto actual = response.getTask();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual.getEntityId()).isEqualTo(rq.getEntityId());
        assertThat(actual.getName()).isEqualTo(rq.getName());
        assertThat(actual.getDescription()).isEqualTo(rq.getDescription());
        assertThat(actual.getType().getEntityId()).isEqualTo(rq.getTypeId());
        assertThat(actual.getStatus().getEntityId()).isEqualTo(rq.getStatusId());
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getLastModifiedDate()).isNotNull();
        assertThat(actual.getNumber()).isNotNull();
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeEntityId = randomPositiveLong();

        String json = delete("/" + fakeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
        assertThat(response.getStatus().getMessage()).isNull();
    }

    @Test
    void deleteByEntityId_found() {
        TasksBoard board = insertTasksBoard();
        TaskDto saved = insertTask(board);

        String json = delete("/" + saved.getEntityId());

        assertThat(json).isEmpty();
    }

    private TaskDto insertTask(TasksBoard board) {
        Task created = randomTaskBuilder().setBoard(board).build();
        return map(service.save(created));
    }

    private TasksBoard insertTasksBoard() {
        TasksBoard board = randomTasksBoard();
        return boardService.save(board);
    }
}
