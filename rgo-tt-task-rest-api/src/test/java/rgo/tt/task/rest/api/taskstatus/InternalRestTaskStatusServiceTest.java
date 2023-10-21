package rgo.tt.task.rest.api.taskstatus;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.task.rest.api.RestConfig;
import rgo.tt.task.rest.api.taskstatus.dto.TaskStatusDto;
import rgo.tt.task.rest.api.taskstatus.response.TaskStatusGetListResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.get;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startServerWithService;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.STATUSES;
import static rgo.tt.task.rest.api.taskstatus.TaskStatusMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class InternalRestTaskStatusServiceTest {

    @Autowired private RestTaskStatusService restService;

    @BeforeEach
    void setUp() {
        startServerWithService(restService);
        H2PersistenceUtils.truncateTables();
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findAll() {
        String json = get();
        TaskStatusGetListResponse response = fromJson(json, TaskStatusGetListResponse.class);
        List<TaskStatusDto> actual = response.getTaskStatuses();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(map(STATUSES));
    }
}
