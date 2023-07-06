package rgo.tt.main.service.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.validator.ValidateException;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;
import rgo.tt.main.service.config.ServiceConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
class TaskServiceTest {

    @Autowired TaskService service;
    @Autowired DbTxManager tx;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
    }

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
    void findBySoftlyName_invalidRq_nameIdIsNull() {
        assertThrows(ValidateException.class, () -> service.findSoftlyByName(null), "The name is null.");
    }

    @Test
    void findBySoftlyName_invalidRq_nameIsEmpty() {
        String emptyName = "";
        assertThrows(ValidateException.class, () -> service.findSoftlyByName(emptyName), "The name is empty.");
    }

    @Test
    void save_invalidRq_nameIsNull() {
        Task task = Task.builder().build();
        assertThrows(ValidateException.class, () -> service.save(task), "The name is null.");
    }

    @Test
    void save_invalidRq_nameIsEmpty() {
        String name = "";
        Task task = Task.builder().setName(name).build();
        assertThrows(ValidateException.class, () -> service.save(task), "The name is empty.");
    }

    @Test
    void update_invalidRq_nameIsNull() {
        Task task = Task.builder().setEntityId(randomPositiveLong()).build();
        assertThrows(ValidateException.class, () -> service.update(task), "The name is null.");
    }

    @Test
    void update_invalidRq_nameIsEmpty() {
        String name = "";
        Task task = Task.builder().setEntityId(randomPositiveLong()).setName(name).build();
        assertThrows(ValidateException.class, () -> service.update(task), "The name is empty.");
    }

    @Test
    void update_invalidRq_entityIdIsNull() {
        Task task = Task.builder().setName(randomString()).build();
        assertThrows(ValidateException.class, () -> service.update(task), "The entityId is null.");
    }

    @Test
    void update_invalidRq_entityIdIsNegative() {
        Long entityId = -randomPositiveLong();
        Task task = Task.builder().setEntityId(entityId).setName(randomString()).build();
        assertThrows(ValidateException.class, () -> service.update(task), "The entityId is negative.");
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
