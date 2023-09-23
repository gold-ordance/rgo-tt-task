package rgo.tt.task.persistence.storage.repository.taskstatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.task.persistence.config.PersistenceConfig;
import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.STATUSES;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TaskStatusRepositoryTest {

    @Autowired private TaskStatusRepository repository;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
    }

    @Test
    void findAll() {
        List<TaskStatus> tasks = repository.findAll();
        assertThat(tasks).containsExactlyInAnyOrderElementsOf(STATUSES);
    }
}
