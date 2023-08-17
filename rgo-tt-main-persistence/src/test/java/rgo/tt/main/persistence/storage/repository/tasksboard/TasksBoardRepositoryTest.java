package rgo.tt.main.persistence.storage.repository.tasksboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.utils.EntityGenerator;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTasksBoard;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TasksBoardRepositoryTest {

    private static final int TASKS_BOARDS_LIMIT = 32;

    @Autowired private TasksBoardRepository repository;
    @Autowired private DbTxManager tx;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
    }

    @Test
    void findAll() {
        List<TasksBoard> expected = insertRandomBoards();

        List<TasksBoard> boards = repository.findAll();
        assertIterableEquals(expected, boards);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();

        Optional<TasksBoard> opt = repository.findByEntityId(fakeId);
        assertFalse(opt.isPresent());
    }

    @Test
    void findByEntityId_found() {
        TasksBoard created = randomTasksBoard();
        TasksBoard expected = insert(created);

        Optional<TasksBoard> opt = repository.findByEntityId(expected.getEntityId());
        assertTrue(opt.isPresent());
        assertEquals(expected, opt.get());
    }

    @Test
    void save() {
        TasksBoard created = randomTasksBoard();

        TasksBoard savedBoard = repository.save(created);
        assertEquals(created.getName(), savedBoard.getName());
        assertEquals(created.getShortName(), savedBoard.getShortName());
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        assertFalse(repository.deleteByEntityId(fakeId));
    }

    @Test
    void deleteByEntityId_found() {
        TasksBoard created =  randomTasksBoard();
        TasksBoard saved = insert(created);

        assertTrue(repository.deleteByEntityId(saved.getEntityId()));
    }

    private List<TasksBoard> insertRandomBoards() {
        int limit = ThreadLocalRandom.current().nextInt(TASKS_BOARDS_LIMIT);
        return Stream.generate(EntityGenerator::randomTasksBoard)
                .limit(limit)
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private TasksBoard insert(TasksBoard board) {
        return repository.save(board);
    }
}
