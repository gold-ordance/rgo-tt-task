package rgo.tt.task.rest.api.tasksboard;

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
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.delete;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.get;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.post;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startArmeriaServer;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.task.rest.api.RequestGenerator.createTasksBoardSaveRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class ValidateRestTasksBoardServiceDecoratorTest {

    @Autowired private RestTasksBoardService restService;

    @BeforeEach
    void setUp() {
        startArmeriaServer(restService);
    }

    @AfterAll
    static void afterAll() {
        stopServer();
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
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        rq.setName(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();
        rq.setName("");

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The name is empty.");
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
