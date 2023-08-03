package rgo.tt.main.persistence.storage.repository.taskstatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.STATUSES;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TaskStatusRepositoryTest {

    @Autowired private TaskStatusRepository repository;
    @Autowired private DbTxManager tx;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
    }

    @Test
    void findAll() {
        List<TaskStatus> tasks = repository.findAll();
        assertIterableEquals(STATUSES, tasks);
    }
}
