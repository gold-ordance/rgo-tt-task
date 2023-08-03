package rgo.tt.main.service.tasksboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.validator.ValidateException;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.service.ServiceConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoardBuilder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
class TasksBoardServiceTest {

    @Autowired private TasksBoardService service;

    @Test
    void findByEntityId_invalidRq_entityIdIsNull() {
        assertThrows(ValidateException.class, () -> service.findByEntityId(null), "The entityId is null.");
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNegative() {
        Long fakeId = -randomPositiveLong();
        assertThrows(ValidateException.class, () -> service.findByEntityId(fakeId), "The entityId is negative.");
    }

    @Test
    void save_invalidRq_nameIsNull() {
        TasksBoard board = randomTasksBoardBuilder().setName(null).build();
        assertThrows(ValidateException.class, () -> service.save(board), "The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        TasksBoard board = randomTasksBoardBuilder().setName("").build();
        assertThrows(ValidateException.class, () -> service.save(board), "The name is empty.");
    }

    @Test
    void update_invalidRq_entityIdIsNull() {
        TasksBoard board = randomTasksBoardBuilder().setEntityId(null).build();
        assertThrows(ValidateException.class, () -> service.update(board), "The entityId is null.");
    }

    @Test
    void update_invalidRq_entityIdIsNegative() {
        TasksBoard board = randomTasksBoardBuilder().setEntityId(-randomPositiveLong()).build();
        assertThrows(ValidateException.class, () -> service.update(board), "The entityId is negative.");
    }

    @Test
    void update_invalidRq_nameIsNull() {
        TasksBoard board = randomTasksBoardBuilder().setName(null).build();
        assertThrows(ValidateException.class, () -> service.update(board), "The name is null.");
    }

    @Test
    void update_invalidRq_nameIsEmpty() {
        TasksBoard board = randomTasksBoardBuilder().setName("").build();
        assertThrows(ValidateException.class, () -> service.update(board), "The name is empty.");
    }

    @Test
    void deleteByEntityId_invalidRq_entityIdIsNull() {
        assertThrows(ValidateException.class, () -> service.deleteByEntityId(null), "The entityId is null.");
    }

    @Test
    void deleteByEntityId_invalidRq_entityIdIsNegative() {
        Long fakeId = -randomPositiveLong();
        assertThrows(ValidateException.class, () -> service.deleteByEntityId(fakeId), "The entityId is negative.");
    }
}
