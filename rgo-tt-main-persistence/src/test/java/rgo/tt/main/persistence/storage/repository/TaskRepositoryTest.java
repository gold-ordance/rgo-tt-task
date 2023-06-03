package rgo.tt.main.persistence.storage.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.main.persistence.EntityGenerator;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.query.TaskQuery;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static rgo.tt.main.common.utils.CommonUtils.randomPositiveLong;
import static rgo.tt.main.common.utils.CommonUtils.randomString;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TaskRepositoryTest {

    private static final int TASKS_LIMIT = 32;

    @Autowired private TaskRepository repository;
    @Autowired private DbTxManager tx;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
    }

    @Test
    void findAll() {
        List<Task> expected = insertRandomTasks();

        List<Task> tasks = repository.findAll();
        assertIterableEquals(expected, tasks);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();

        Optional<Task> opt = repository.findByEntityId(fakeId);
        assertFalse(opt.isPresent());
    }

    @Test
    void findByEntityId_found() {
        Task created = EntityGenerator.randomTask();
        Task expected = insert(created);

        Optional<Task> opt = repository.findByEntityId(expected.getEntityId());
        assertTrue(opt.isPresent());
        assertEquals(expected, opt.get());
    }

    @Test
    void findSoftlyByName() {
        String name = randomString();

        Task task1 = EntityGenerator.randomTask(name);
        Task task2 = EntityGenerator.randomTask(randomString() + name);
        Task task3 = EntityGenerator.randomTask(name + randomString());
        Task task4 = EntityGenerator.randomTask(randomString() + name + randomString());

        List<Task> tasks = List.of(task1, task2, task3, task4);
        List<Task> savedTasks = insertTasks(tasks);

        List<Task> actualTasks = repository.findSoftlyByName(name);
        assertIterableEquals(savedTasks, actualTasks);
    }

    @Test
    void save() {
        Task created = EntityGenerator.randomTask();

        Task savedTask = repository.save(created);
        assertEquals(created.getName(), savedTask.getName());
    }

    @Test
    void update() {
        Task created = EntityGenerator.randomTask();
        Task saved = insert(created);

        Task updated = EntityGenerator.randomTask(saved.getEntityId());
        Task actual = repository.update(updated);

        assertEquals(saved.getEntityId(), actual.getEntityId());
        assertNotEquals(saved.getName(), actual.getName());
        assertEquals(updated, actual);
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeId = randomPositiveLong();

        assertThrows(RuntimeException.class, () -> repository.deleteByEntityId(fakeId), "Task delete by entity id error.");
    }

    @Test
    void deleteByEntityId_found() {
        Task created = EntityGenerator.randomTask();
        Task saved = insert(created);

        assertDoesNotThrow(() -> repository.deleteByEntityId(saved.getEntityId()));
    }

    private List<Task> insertRandomTasks() {
        int limit = ThreadLocalRandom.current().nextInt(TASKS_LIMIT);
        return Stream.generate(EntityGenerator::randomTask)
                .limit(limit)
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private List<Task> insertTasks(List<Task> tasks) {
        return tasks.stream()
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private Task insert(Task task) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
                "name", task.getName()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = tx.jdbc().update(TaskQuery.save(), params, keyHolder, new String[]{"entity_id"});
        Number key = keyHolder.getKey();

        if (result != 1 || key == null) {
            Assertions.fail("The task not saved.");
        }

        MapSqlParameterSource params2 = new MapSqlParameterSource("entity_id", key.longValue());
        List<Task> tasks = tx.jdbc().query(TaskQuery.findByEntityId(), params2, TaskRepository.mapper);

        if (tasks.size() != 1) {
            Assertions.fail("The task not found.");
        }

        return tasks.get(0);
    }
}
