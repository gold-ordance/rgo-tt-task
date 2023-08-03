package rgo.tt.main.persistence.storage.repository.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.main.persistence.config.PersistenceConfig;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.main.persistence.storage.utils.EntityGenerator;
import rgo.tt.main.persistence.storage.utils.PersistenceUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TaskRepositoryTest {

    private static final int TASKS_LIMIT = 32;
    private static final int TASKS_BOARDS_LIMIT = 8;

    @Autowired private TaskRepository repository;
    @Autowired private TasksBoardRepository boardRepository;
    @Autowired private DbTxManager tx;

    @BeforeEach
    void setUp() {
        PersistenceUtils.truncateTables(tx);
    }

    @Test
    void findAll_boardIdIsFake() {
        Long fakeBoardId = randomPositiveLong();
        assertThrows(InvalidEntityException.class, () -> repository.findAll(fakeBoardId), "The boardId not found in the storage.");
    }

    @Test
    void findAll() {
        TasksBoard board = insertBoard(randomTasksBoard());
        List<Task> expected = insertRandomTasks(board);

        List<Task> tasks = repository.findAll(board.getEntityId());
        assertIterableEquals(expected, tasks);
    }

    @Test
    void findAll_manyBoards() {
        List<TasksBoard> boards = Stream.generate(EntityGenerator::randomTasksBoard)
                .limit(TASKS_BOARDS_LIMIT)
                .map(this::insertBoard)
                .collect(Collectors.toList());

        List<List<Task>> expectedList = boards.stream()
                .map(this::insertRandomTasks)
                .collect(Collectors.toList());

        for (int i = 0; i < boards.size(); i++) {
            List<Task> tasks = repository.findAll(boards.get(i).getEntityId());
            assertIterableEquals(expectedList.get(i), tasks);
        }
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();

        Optional<Task> opt = repository.findByEntityId(fakeId);
        assertFalse(opt.isPresent());
    }

    @Test
    void findByEntityId_found() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();
        Task expected = insert(created);

        Optional<Task> opt = repository.findByEntityId(expected.getEntityId());
        assertTrue(opt.isPresent());
        assertEquals(expected, opt.get());
    }

    @Test
    void findSoftlyByName_boardIdIsFake() {
        String name = randomString();
        Long fakeBoardId = randomPositiveLong();
        assertThrows(InvalidEntityException.class, () -> repository.findSoftlyByName(name, fakeBoardId), "The boardId not found in the storage.");
    }

    @Test
    void findSoftlyByName() {
        String name = randomString();
        TasksBoard board1 = insertBoard(randomTasksBoard());
        TasksBoard board2 = insertBoard(randomTasksBoard());

        Task task1 = randomTaskBuilder().setBoard(board1).setName(name).build();
        Task task2 = randomTaskBuilder().setBoard(board1).setName(randomString() + name).build();
        Task task3 = randomTaskBuilder().setBoard(board1).setName(name + randomString()).build();
        Task task4 = randomTaskBuilder().setBoard(board1).setName(randomString() + name + randomString()).build();
        Task task5 = randomTaskBuilder().setBoard(board2).setName(name).build();

        List<Task> tasks = List.of(task1, task2, task3, task4);
        List<Task> savedTasks = insertTasks(tasks);
        Task savedTask = insert(task5);

        List<Task> actualTasks = repository.findSoftlyByName(name, board1.getEntityId());
        assertFalse(actualTasks.contains(savedTask));
        assertIterableEquals(savedTasks, actualTasks);
    }

    @Test
    void save_boardIsFake() {
        TasksBoard fakeBoard = randomTasksBoard();
        Task created = randomTaskBuilder().setBoard(fakeBoard).build();
        assertThrows(InvalidEntityException.class, () -> repository.save(created), "The boardId not found in the storage.");
    }

    @Test
    void save() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();

        Task savedTask = repository.save(created);
        assertEquals(created.getName(), savedTask.getName());
        assertEquals(TO_DO, savedTask.getStatus());
        assertEquals(board, savedTask.getBoard());
    }

    @Test
    void update_entityIdIsFake() {
        Task created = randomTask();
        assertThrows(BaseException.class, () -> repository.update(created), "The entityId not found in the storage.");
    }

    @Test
    void update_statusIdIsFake() {
        Task created = randomTaskBuilder().setStatus(randomTaskStatus()).build();
        assertThrows(BaseException.class, () -> repository.update(created), "The statusId not found in the storage.");
    }

    @Test
    void update() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created =  randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);

        Task updated = randomTaskBuilder().setEntityId(saved.getEntityId()).build();
        Task actual = repository.update(updated);

        assertEquals(saved.getEntityId(), actual.getEntityId());
        assertEquals(updated.getName(), actual.getName());
        assertEquals(saved.getCreatedDate(), actual.getCreatedDate());
        assertNotEquals(saved.getLastModifiedDate(), actual.getLastModifiedDate());
        assertEquals(updated.getStatus(), actual.getStatus());
        assertEquals(saved.getBoard(), actual.getBoard());
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        assertFalse(repository.deleteByEntityId(fakeId));
    }

    @Test
    void deleteByEntityId_found() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created =  randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);

        assertTrue(repository.deleteByEntityId(saved.getEntityId()));
    }

    private List<Task> insertRandomTasks(TasksBoard board) {
        int limit = ThreadLocalRandom.current().nextInt(TASKS_LIMIT);
        return Stream.generate(() -> randomTaskBuilder().setBoard(board).build())
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
        return repository.save(task);
    }

    private TasksBoard insertBoard(TasksBoard board) {
        return boardRepository.save(board);
    }
}
