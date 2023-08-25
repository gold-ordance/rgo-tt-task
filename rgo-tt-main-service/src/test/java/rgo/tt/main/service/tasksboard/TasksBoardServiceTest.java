package rgo.tt.main.service.tasksboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.validator.ValidateException;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.service.ServiceConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rgo.tt.common.utils.HelperUtils.getFirstSymbol;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.TestUtils.assertThrowsWithMessage;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoard;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoardBuilder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
class TasksBoardServiceTest {

    @Autowired private TasksBoardService service;

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
    void save_invalidRq_nameIsNull() {
        TasksBoard board = randomTasksBoardBuilder()
                .setName(null)
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(board),
                "The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        TasksBoard board = randomTasksBoardBuilder()
                .setName("")
                .build();

        assertThrowsWithMessage(
                ValidateException.class,
                () -> service.save(board),
                "The name is empty.");
    }

    @Test
    void save() {
        TasksBoard created = randomTasksBoard();
        TasksBoard actual = service.save(created);

        assertEquals(created.getName(), actual.getName());
        assertEquals(getFirstSymbol(created.getName()).toUpperCase(), actual.getShortName());
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
