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
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.persistence.storage.utils.EntityGenerator;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.task.rest.api.RestConfig;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardGetEntityResponse;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardGetListResponse;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardModifyResponse;
import rgo.tt.task.service.tasksboard.TasksBoardService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.delete;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.get;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.post;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.startServerWithService;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.HelperUtils.getFirstSymbol;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;
import static rgo.tt.task.rest.api.RequestGenerator.createTasksBoardSaveRequest;
import static rgo.tt.task.rest.api.tasksboard.TasksBoardMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class InternalRestTasksBoardServiceTest {

    @Autowired private RestTasksBoardService restService;
    @Autowired private TasksBoardService service;

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
        List<TasksBoardDto> expected = insertRandomClients();

        String json = get();
        TasksBoardGetListResponse response = fromJson(json, TasksBoardGetListResponse.class);
        List<TasksBoardDto> actual = response.getBoards();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeEntityId = randomPositiveLong();

        String json = get("/" + fakeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
        assertThat(response.getStatus().getMessage()).isNull();
    }

    @Test
    void findByEntityId_found() {
        TasksBoardDto expected = insert(randomTasksBoard());

        String json = get("/" + expected.getEntityId());
        TasksBoardGetEntityResponse response = fromJson(json, TasksBoardGetEntityResponse.class);
        TasksBoardDto actual = response.getBoard();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void save() {
        TasksBoardSaveRequest rq = createTasksBoardSaveRequest();

        String json = post(json(rq));
        TasksBoardModifyResponse response = fromJson(json, TasksBoardModifyResponse.class);
        TasksBoardDto actual = response.getBoard();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.STORED);
        assertThat(actual.getEntityId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(rq.getName());
        assertThat(actual.getShortName()).isEqualTo(getFirstSymbol(rq.getName()).toUpperCase());
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
        TasksBoardDto expected = insert(randomTasksBoard());
        String json = delete("/" + expected.getEntityId());
        assertThat(json).isEmpty();
    }

    private List<TasksBoardDto> insertRandomClients() {
        int limit = ThreadLocalRandom.current().nextInt(1, 10);
        return Stream.generate(EntityGenerator::randomTasksBoard)
                .limit(limit)
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private TasksBoardDto insert(TasksBoard board) {
        return map(service.save(board));
    }
}
