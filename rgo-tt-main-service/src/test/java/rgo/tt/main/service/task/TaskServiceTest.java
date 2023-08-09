package rgo.tt.main.service.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.validator.ValidateException;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.service.ServiceConfig;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.common.utils.TestUtils.assertThrowsWithMessage;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskBuilder;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskStatusBuilder;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskTypeBuilder;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoardBuilder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
class TaskServiceTest {

    @Autowired private TaskService service;

    @Test
    void findAll_invalidRq_boardIdIsNull() {
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findAll(null),
                "The boardId is null.");
    }

    @Test
    void findAll_invalidRq_boardIdIsNegative() {
        long fakeBoardId = -randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findAll(fakeBoardId),
                "The boardId is negative.");
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNull() {
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findByEntityId(null),
                "The entityId is null.");
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNegative() {
        long fakeId = -randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findByEntityId(fakeId),
                "The entityId is negative.");
    }

    @Test
    void findBySoftlyName_invalidRq_nameIsNull() {
        long boardId = randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findSoftlyByName(null, boardId),
                "The name is null.");
    }

    @Test
    void findBySoftlyName_invalidRq_nameIsEmpty() {
        String emptyName = "";
        long boardId = randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findSoftlyByName(emptyName, boardId),
                "The name is empty.");
    }

    @Test
    void findBySoftlyName_invalidRq_boardIdIsNull() {
        String name = randomString();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findSoftlyByName(name, null),
                "The boardId is null.");
    }

    @Test
    void findBySoftlyName_invalidRq_boardIdIsNegative() {
        String name = randomString();
        long fakeBoardId = -randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.findSoftlyByName(name, fakeBoardId),
                "The boardId is negative.");
    }

    @Test
    void save_invalidRq_nameIsNull() {
        Task task = randomTaskBuilder()
                .setName(null)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        Task task = randomTaskBuilder()
                .setName("")
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The name is empty.");
    }

    @Test
    void save_invalidRq_boardIdIsNull() {
        TasksBoard board = randomTasksBoardBuilder()
                .setEntityId(null)
                .build();

        Task task = randomTaskBuilder()
                .setBoard(board)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The boardId is null.");
    }

    @Test
    void save_invalidRq_boardIdIsNegative() {
        TasksBoard board = randomTasksBoardBuilder()
                .setEntityId(-randomPositiveLong())
                .build();

        Task task = randomTaskBuilder()
                .setBoard(board)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The boardId is negative.");
    }

    @Test
    void save_invalidRq_typeIdIsNull() {
        TaskType type = randomTaskTypeBuilder()
                .setEntityId(null)
                .build();

        Task task = randomTaskBuilder()
                .setType(type)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The typeId is null.");
    }

    @Test
    void save_invalidRq_typeIdIsNegative() {
        TaskType type = randomTaskTypeBuilder()
                .setEntityId(-randomPositiveLong())
                .build();

        Task task = randomTaskBuilder()
                .setType(type)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(task),
                "The typeId is negative.");
    }

    @Test
    void update_invalidRq_entityIdIsNull() {
        Task task = randomTaskBuilder()
                .setEntityId(null)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The entityId is null.");
    }

    @Test
    void update_invalidRq_entityIdIsNegative() {
        Task task = randomTaskBuilder()
                .setEntityId(-randomPositiveLong())
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The entityId is negative.");
    }

    @Test
    void update_invalidRq_nameIsNull() {
        Task task = randomTaskBuilder()
                .setName(null)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The name is null.");
    }

    @Test
    void update_invalidRq_nameIsEmpty() {
        Task task = randomTaskBuilder()
                .setName("")
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The name is empty.");
    }

    @Test
    void update_invalidRq_typeIdIsNull() {
        Task task = randomTaskBuilder()
                .setType(randomTaskTypeBuilder()
                        .setEntityId(null)
                        .build())
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The typeId is null.");
    }

    @Test
    void update_invalidRq_typeIdIsNegative() {
        Task task = randomTaskBuilder()
                .setType(randomTaskTypeBuilder()
                        .setEntityId(-randomPositiveLong())
                        .build())
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The typeId is negative.");
    }

    @Test
    void update_invalidRq_statusIdIsNull() {
        Task task = randomTaskBuilder()
                .setStatus(randomTaskStatusBuilder()
                        .setEntityId(null)
                        .build())
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The statusId is null.");
    }

    @Test
    void update_invalidRq_statusIdIsNegative() {
        Task task = randomTaskBuilder()
                .setStatus(randomTaskStatusBuilder()
                        .setEntityId(-randomPositiveLong())
                        .build())
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.update(task),
                "The statusId is negative.");
    }

    @Test
    void deleteByEntityId_invalidRq_entityIdIsNull() {
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.deleteByEntityId(null),
                "The entityId is null.");
    }

    @Test
    void deleteByEntityId_invalidRq_entityIdIsNegative() {
        long fakeId = -randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.deleteByEntityId(fakeId),
                "The entityId is negative.");
    }
}
