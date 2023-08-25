package rgo.tt.main.rest.api.tasksboard;

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
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.main.service.tasksboard.TasksBoardService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rgo.tt.common.rest.api.RestUtils.json;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoard;
import static rgo.tt.main.rest.api.RequestGenerator.createTasksBoardSaveRequest;

@SpringBootTest
@WebAppConfiguration
class TasksBoardRestControllerTest {

    @Autowired private WebApplicationContext context;
    @Autowired private TasksBoardService service;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
        mvc =  MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAll_empty() throws Exception {
        int taskSize = 0;

        mvc.perform(get(TasksBoardRestController.BASE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.boards", hasSize(taskSize)));
    }

    @Test
    void getAll() throws Exception {
        TasksBoard board = insertTasksBoard();
        int taskSize = 1;

        mvc.perform(get(TasksBoardRestController.BASE_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.boards", hasSize(taskSize)))
                .andExpect(jsonPath("$.boards[0].entityId", is(board.getEntityId().intValue())))
                .andExpect(jsonPath("$.boards[0].name", is(board.getName())));
    }

    @Test
    void getByEntityId_entityIdIsFake() throws Exception {
        long fakeEntityId = randomPositiveLong();

        mvc.perform(get(TasksBoardRestController.BASE_URL + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void getByEntityId() throws Exception {
        TasksBoard board = insertTasksBoard();

        mvc.perform(get(TasksBoardRestController.BASE_URL + "/" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.board.entityId", is(board.getEntityId().intValue())))
                .andExpect(jsonPath("$.board.name", is(board.getName())));
    }

    @Test
    void save_invalidRq_nameIsNull() throws Exception {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        rq.setName(null);

        mvc.perform(post(TasksBoardRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is null.")));
    }

    @Test
    void save_invalidRq_nameIsEmpty() throws Exception {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        rq.setName("");

        mvc.perform(post(TasksBoardRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void save() throws Exception {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();

        mvc.perform(post(TasksBoardRestController.BASE_URL).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.STORED.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.STORED.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.board.name", is(rq.getName())));
    }

    @Test
    void deleteByEntityId_entityIdIsFake() throws Exception {
        long fakeEntityId = randomPositiveLong();

        mvc.perform(delete(TasksBoardRestController.BASE_URL + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void deleteByEntityId() throws Exception {
        TasksBoard board = insertTasksBoard();

        mvc.perform(delete(TasksBoardRestController.BASE_URL + "/" + board.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NO_CONTENT.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NO_CONTENT.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    private TasksBoard insertTasksBoard() {
        TasksBoard created = randomTasksBoard();
        return service.save(created);
    }
}
