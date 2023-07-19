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
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.service.task.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rgo.tt.common.rest.api.RestUtils.json;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTask;

@SpringBootTest
@WebAppConfiguration
class TaskRestControllerTest {

    private static final String BASE_ENDPOINT = "/tasks";

    @Autowired private WebApplicationContext context;
    @Autowired private TaskService service;
    @Autowired private DbTxManager tx;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
        mvc =  MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAll_empty() throws Exception {
        int taskSize = 0;

        mvc.perform(get(BASE_ENDPOINT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(taskSize)));
    }

    @Test
    void getAll_nonEmpty() throws Exception {
        int tasksSize = 1;
        Task saved = insertTask();

        mvc.perform(get(BASE_ENDPOINT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))));
    }

    @Test
    void getByEntityId_entityIdIsFake() throws Exception {
        Long fakeEntityId = randomPositiveLong();

        mvc.perform(get(BASE_ENDPOINT + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void getByEntityId() throws Exception {
        Task saved = insertTask();

        mvc.perform(get(BASE_ENDPOINT + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.name", is(saved.getName())))
                .andExpect(jsonPath("$.task.createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.task.lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))));
    }

    @Test
    void getByName_nameIsEmpty() throws Exception {
        mvc.perform(get(BASE_ENDPOINT + "?name="))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void getByName_nameIsFake() throws Exception {
        int taskSize = 0;
        String fakeName = randomString();

        mvc.perform(get(BASE_ENDPOINT + "?name=" + fakeName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(taskSize)));
    }

    @Test
    void getByName() throws Exception {
        int tasksSize = 1;
        Task saved = insertTask();

        mvc.perform(get(BASE_ENDPOINT + "?name=" + saved.getName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.tasks", hasSize(tasksSize)))
                .andExpect(jsonPath("$.tasks[0].entityId", is(saved.getEntityId().intValue())))
                .andExpect(jsonPath("$.tasks[0].name", is(saved.getName())))
                .andExpect(jsonPath("$.tasks[0].createdDate", startsWith(toString(saved.getCreatedDate()))))
                .andExpect(jsonPath("$.tasks[0].lastModifiedDate", startsWith(toString(saved.getLastModifiedDate()))));
    }

    @Test
    void save_invalidRq_nameIsNull() throws Exception {
        TaskSaveRequest rq = new TaskSaveRequest();

        mvc.perform(post(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is null.")));
    }

    @Test
    void save_invalidRq_nameIsEmpty() throws Exception {
        TaskSaveRequest rq = new TaskSaveRequest();
        rq.setName("");

        mvc.perform(post(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void save() throws Exception {
        TaskSaveRequest rq = new TaskSaveRequest();
        rq.setName(randomString());

        mvc.perform(post(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.STORED.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.STORED.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", notNullValue()))
                .andExpect(jsonPath("$.task.name", is(rq.getName())))
                .andExpect(jsonPath("$.task.createdDate", notNullValue()))
                .andExpect(jsonPath("$.task.lastModifiedDate", notNullValue()));
    }

    @Test
    void put_invalidRq_entityIdIsNull() throws Exception {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setName(randomString());

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId is null.")));
    }

    @Test
    void put_invalidRq_entityIdIsNegative() throws Exception {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(-randomPositiveLong());
        rq.setName(randomString());

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId is negative.")));
    }

    @Test
    void put_invalidRq_nameIsNull() throws Exception {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is null.")));
    }

    @Test
    void put_invalidRq_nameIsEmpty() throws Exception {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());
        rq.setName("");

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_RQ.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_RQ.name())))
                .andExpect(jsonPath("$.status.message", is("The name is empty.")));
    }

    @Test
    void put_invalidEntity_entityIdIsFake() throws Exception {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());
        rq.setName(randomString());

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.INVALID_ENTITY.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.INVALID_ENTITY.name())))
                .andExpect(jsonPath("$.status.message", is("The entityId not found in the storage.")));
    }

    @Test
    void putTask() throws Exception {
        Task saved = insertTask();
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(saved.getEntityId());
        rq.setName(randomString());

        mvc.perform(put(BASE_ENDPOINT).content(json(rq)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.SUCCESS.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.SUCCESS.name())))
                .andExpect(jsonPath("$.status.message", nullValue()))
                .andExpect(jsonPath("$.task.entityId", is(rq.getEntityId().intValue())))
                .andExpect(jsonPath("$.task.name", is(rq.getName())))
                .andExpect(jsonPath("$.task.createdDate", notNullValue()))
                .andExpect(jsonPath("$.task.lastModifiedDate", notNullValue()));
    }

    @Test
    void deleteByEntityId_entityIdIsFake() throws Exception {
        Long fakeEntityId = randomPositiveLong();

        mvc.perform(delete(BASE_ENDPOINT + "/" + fakeEntityId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NOT_FOUND.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NOT_FOUND.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    @Test
    void deleteByEntityId() throws Exception {
        Task saved = insertTask();

        mvc.perform(delete(BASE_ENDPOINT + "/" + saved.getEntityId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(StatusCode.NO_CONTENT.getHttpCode()))
                .andExpect(jsonPath("$.status.statusCode", is(StatusCode.NO_CONTENT.name())))
                .andExpect(jsonPath("$.status.message", nullValue()));
    }

    private static String toString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    private Task insertTask() {
        Task created = randomTask();
        return service.save(created);
    }
}
