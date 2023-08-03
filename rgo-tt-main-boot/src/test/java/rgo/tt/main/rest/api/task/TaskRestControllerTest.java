package rgo.tt.main.rest.api.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.tasksboard.TasksBoardRestController;
import rgo.tt.main.service.task.TaskService;
import rgo.tt.main.service.tasksboard.TasksBoardService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rgo.tt.common.rest.api.RestUtils.json;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.*;
import static rgo.tt.main.rest.api.RequestGenerator.createTaskPutRequest;
import static rgo.tt.main.rest.api.RequestGenerator.createTaskSaveRequest;

@SpringBootTest
@WebAppConfiguration
class TaskRestControllerTest {

    @Autowired private WebApplicationContext context;
    @Autowired private TaskService service;
    @Autowired private TasksBoardService boardService;
    @Autowired private DbTxManager tx;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAll_boardIdIsNull() throws Exception {
        mvc.perform(get(TaskRestController.BASE_URL + "?boardId="))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId is null.")));
    }

    @Test
    void getAll_boardIdIsFake() throws Exception {
        long boardId = randomPositiveLong();

        mvc.perform(get(TaskRestController.BASE_URL + "?boardId=" + boardId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_ENTITY.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_ENTITY.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId not found in the storage.")));
    }

    @Test
    void getAll_empty() throws Exception {
        TasksBoard board = insertTasksBoard();
        int taskSize = 0;

        mvc.perform(get(TaskRestController.BASE_URL + "?boardId=" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(taskSize)));
    }

    @Test
    void getAll_oneBoard() throws Exception {
        int tasksSize = 1;
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        mvc.perform(get(TaskRestController.BASE_URL + "?boardId=" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))))
                .andExpect(jsonPath("$.tasks[0].status.entityId", is(saved.getStatus().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].status.name", is(saved.getStatus().getName())))
                .andExpect(jsonPath("$.tasks[0].board.entityId", is(saved.getBoard().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].board.name", is(saved.getBoard().getName())))
                .andExpect(jsonPath("$.tasks[0].description", is(saved.getDescription())));
    }

    @Test
    void getAll_twoBoard() throws Exception {
        int tasksSize = 1;

        TasksBoard board1 = insertTasksBoard();
        Task saved1 = insertTask(board1);

        TasksBoard board2 = insertTasksBoard();
        insertTask(board2);

        mvc.perform(get(TaskRestController.BASE_URL + "?boardId=" + board1.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved1.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved1.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved1.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved1.getLastModifiedDate()))))
                .andExpect(jsonPath("$.tasks[0].status.entityId", is(saved1.getStatus().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].status.name", is(saved1.getStatus().getName())))
                .andExpect(jsonPath("$.tasks[0].board.entityId", is(saved1.getBoard().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].board.name", is(saved1.getBoard().getName())))
                .andExpect(jsonPath("$.tasks[0].description", is(saved1.getDescription())));
    }

    @Test
    void getByEntityId_entityIdIsFake() throws Exception {
        long fakeEntityId = randomPositiveLong();

        mvc.perform(get(TaskRestController.BASE_URL + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void getByEntityId() throws Exception {
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        mvc.perform(get(TaskRestController.BASE_URL + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.name", is(saved.getName())))
                .andExpect(jsonPath("$.task.createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.task.lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))))
                .andExpect(jsonPath("$.task.status.entityId", is(saved.getStatus().getEntityId().intValue())))
                .andExpect(jsonPath("$.task.status.name", is(saved.getStatus().getName())))
                .andExpect(jsonPath("$.task.board.entityId", is(saved.getBoard().getEntityId().intValue())))
                .andExpect(jsonPath("$.task.board.name", is(saved.getBoard().getName())))
                .andExpect(jsonPath("$.task.description", is(saved.getDescription())));
    }

    @Test
    void getByName_nameIsEmpty() throws Exception {
        TasksBoard board = insertTasksBoard();

        mvc.perform(get(TaskRestController.BASE_URL + "?name=&boardId=" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void getByName_nameIsFake() throws Exception {
        int taskSize = 0;
        TasksBoard board = insertTasksBoard();
        String fakeName = randomString();

        mvc.perform(get(TaskRestController.BASE_URL + "?name=" + fakeName + "&boardId=" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(taskSize)));
    }

    @Test
    void getByName_boardIdIsNull() throws Exception {
        String name = randomString();

        mvc.perform(get(TaskRestController.BASE_URL + "?name=" + name + "&boardId="))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId is null.")));
    }

    @Test
    void getByName_boardIdIsFake() throws Exception {
        String name = randomString();
        long boardId = randomPositiveLong();

        mvc.perform(get(TaskRestController.BASE_URL + "?name=" + name + "&boardId=" + boardId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_ENTITY.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_ENTITY.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId not found in the storage.")));
    }

    @Test
    void getByName_oneBoard() throws Exception {
        int tasksSize = 1;
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        mvc.perform(get(TaskRestController.BASE_URL + "?name=" + saved.getName() + "&boardId=" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))))
                .andExpect(jsonPath("$.tasks[0].status.entityId", is(saved.getStatus().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].status.name", is(saved.getStatus().getName())))
                .andExpect(jsonPath("$.tasks[0].board.entityId", is(saved.getBoard().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].board.name", is(saved.getBoard().getName())))
                .andExpect(jsonPath("$.tasks[0].description", is(saved.getDescription())));
    }

    @Test
    void getByName_twoBoard() throws Exception {
        int tasksSize = 1;
        TasksBoard board1 = insertTasksBoard();
        TasksBoard board2 = insertTasksBoard();
        Task saved = insertTask(board1);
        insertTask(board2);

        mvc.perform(get(TaskRestController.BASE_URL + "?name=" + saved.getName() + "&boardId=" + board1.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))))
                .andExpect(jsonPath("$.tasks[0].status.entityId", is(saved.getStatus().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].status.name", is(saved.getStatus().getName())))
                .andExpect(jsonPath("$.tasks[0].board.entityId", is(saved.getBoard().getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].board.name", is(saved.getBoard().getName())))
                .andExpect(jsonPath("$.tasks[0].description", is(saved.getDescription())));
    }

    @Test
    void save_invalidRq_nameIsNull() throws Exception {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setName(null);

        mvc.perform(post(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is null.")));
    }

    @Test
    void save_invalidRq_nameIsEmpty() throws Exception {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setName("");

        mvc.perform(post(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void save_invalidRq_boardIdIsNull() throws Exception {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(null);

        mvc.perform(post(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId is null.")));
    }

    @Test
    void save_invalidRq_boardIdIsNegative() throws Exception {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(-randomPositiveLong());

        mvc.perform(post(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The boardId is negative.")));
    }

    @Test
    void save() throws Exception {
        TasksBoard board = insertTasksBoard();
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(board.getEntityId());

        mvc.perform(post(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.STORED.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.STORED.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", notNullValue()))
                .andExpect(jsonPath("$.task.name", is(rq.getName())))
                .andExpect(jsonPath("$.task.createdDate", notNullValue()))
                .andExpect(jsonPath("$.task.lastModifiedDate", notNullValue()))
                .andExpect(jsonPath("$.task.status.entityId", is(TO_DO.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.status.name", is(TO_DO.getName())))
                .andExpect(jsonPath("$.task.board.entityId", is(board.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.board.name", is(board.getName())))
                .andExpect(jsonPath("$.task.description", is(rq.getDescription())));
    }

    @Test
    void put_invalidRq_entityIdIsNull() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(null);

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId is null.")));
    }

    @Test
    void put_invalidRq_entityIdIsNegative() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(-randomPositiveLong());

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId is negative.")));
    }

    @Test
    void put_invalidRq_nameIsNull() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setName(null);

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is null.")));
    }

    @Test
    void put_invalidRq_nameIsEmpty() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setName("");

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void put_invalidRq_statusIdIsNull() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setStatusId(null);

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The statusId is null.")));
    }

    @Test
    void put_invalidRq_statusIdIsNegative() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setStatusId(-randomPositiveLong());

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The statusId is negative.")));
    }

    @Test
    void put_invalidEntity_entityIdIsFake() throws Exception {
        TaskPutRequest rq = createTaskPutRequest();

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_ENTITY.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_ENTITY.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId not found in the storage.")));
    }

    @Test
    void put_invalidEntity_statusIdIsFake() throws Exception {
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(saved.getEntityId());
        rq.setStatusId(0L);

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_ENTITY.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_ENTITY.name())))
                .andExpect(jsonPath("$.status.message", is("The statusId not found in the storage.")));
    }

    @Test
    void putTask() throws Exception {
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(saved.getEntityId());

        mvc.perform(put(TaskRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", is(rq.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.name", is(rq.getName())))
                .andExpect(jsonPath("$.task.createdDate", notNullValue()))
                .andExpect(jsonPath("$.task.lastModifiedDate", notNullValue()))
                .andExpect(jsonPath("$.task.status.entityId", is(rq.getStatusId().intValue())))
                .andExpect(jsonPath("$.task.description", is(rq.getDescription())));
    }

    @Test
    void deleteByEntityId_entityIdIsFake() throws Exception {
        long fakeEntityId = randomPositiveLong();

        mvc.perform(delete(TaskRestController.BASE_URL + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void deleteByEntityId() throws Exception {
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        mvc.perform(delete(TaskRestController.BASE_URL + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NO_CONTENT.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NO_CONTENT.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void deleteTasksBoardByEntityId_tasksShouldBeDeleted() throws Exception {
        TasksBoard board = insertTasksBoard();
        Task saved = insertTask(board);

        mvc.perform(delete(TasksBoardRestController.BASE_URL + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NO_CONTENT.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NO_CONTENT.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));

        mvc.perform(get(TaskRestController.BASE_URL + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    private static String toString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    private Task insertTask(TasksBoard board) {
        Task created = randomTaskBuilder().setBoard(board).build();
        return service.save(created);
    }

    private TasksBoard insertTasksBoard() {
        TasksBoard created = randomTasksBoard();
        return boardService.save(created);
    }
}
