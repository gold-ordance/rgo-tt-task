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
import rgo.tt.task.rest.api.RestConfig;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.delete;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.get;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.post;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.put;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.startServerWithService;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskPutRequest;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskSaveRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class ValidateRestTaskServiceDecoratorTest {

    @Autowired private RestTaskService restService;

    @BeforeEach
    void setUp() {
        startServerWithService(restService);
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findAllForBoard_invalidRq_boardIdIsNegative() {
        long negativeBoardId = -randomPositiveLong();

        String json = get("?boardId=" + negativeBoardId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId is negative.");
    }

    @Test
    void findBySoftlyName_invalidRq_nameIsEmpty() {
        String name = "";
        long negativeBoardId = randomPositiveLong();

        String json = get("?name=" + name + "&boardId=" + negativeBoardId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is empty.");
    }

    @Test
    void findBySoftlyName_invalidRq_boardIdIsNegative() {
        String name = randomString();
        long negativeBoardId = -randomPositiveLong();

        String json = get("?name=" + name + "&boardId=" + negativeBoardId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId is negative.");
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNegative() {
        long negativeEntityId = -randomPositiveLong();

        String json = get("/" + negativeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is negative.");
    }

    @Test
    void save_invalidRq_nameIsNull() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setName(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setName("");

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is empty.");
    }

    @Test
    void save_invalidRq_boardIdIsNull() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId is null.");
    }

    @Test
    void save_invalidRq_boardIdIsNegative() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setBoardId(-randomPositiveLong());

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The boardId is negative.");

    }

    @Test
    void save_invalidRq_typeIdIsNull() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setTypeId(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId is null.");
    }

    @Test
    void save_invalidRq_typeIdIsNegative() {
        TaskSaveRequest rq = createTaskSaveRequest();
        rq.setTypeId(-randomPositiveLong());

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId is negative.");
    }

    @Test
    void update_invalidRq_entityIdIsNull() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(null);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is null.");
    }

    @Test
    void update_invalidRq_entityIdIsNegative() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setEntityId(-randomPositiveLong());

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is negative.");
    }

    @Test
    void update_invalidRq_nameIsNull() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setName(null);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is null.");
    }

    @Test
    void update_invalidRq_nameIsEmpty() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setName("");

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is empty.");
    }

    @Test
    void update_invalidRq_typeIdIsNull() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setTypeId(null);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId is null.");
    }

    @Test
    void update_invalidRq_typeIdIsNegative() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setTypeId(-randomPositiveLong());

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The typeId is negative.");
    }

    @Test
    void update_invalidRq_statusIdIsNull() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setStatusId(null);

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The statusId is null.");
    }

    @Test
    void update_invalidRq_statusIdIsNegative() {
        TaskPutRequest rq = createTaskPutRequest();
        rq.setStatusId(-randomPositiveLong());

        String json = put(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The statusId is negative.");
    }

    @Test
    void deleteByEntityId_invalidRq_entityIdIsNegative() {
        long negativeEntityId = -randomPositiveLong();

        String json = delete("/" + negativeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is negative.");
    }
}
