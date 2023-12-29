package rgo.tt.task.rest.api.tasktype;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.task.rest.api.RestConfig;
import rgo.tt.task.rest.api.tasktype.dto.TaskTypeDto;
import rgo.tt.task.rest.api.tasktype.response.TaskTypeGetListResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startArmeriaServer;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.common.armeria.rest.RestUtils.fromJson;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.TYPES;
import static rgo.tt.task.rest.api.tasktype.TaskTypeMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class InternalRestTaskTypeServiceTest {

    @Autowired private RestTaskTypeService restService;

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
    void findAll() {
        String json = ArmeriaClientManager.get();
        TaskTypeGetListResponse response = fromJson(json, TaskTypeGetListResponse.class);
        List<TaskTypeDto> actual = response.getTypes();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(map(TYPES));
    }
}
