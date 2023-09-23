package rgo.tt.task.persistence.storage.repository.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.task.persistence.config.PersistenceConfig;
import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.persistence.storage.entity.TaskType;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.persistence.storage.repository.tasksboard.TasksBoardRepository;
import rgo.tt.task.persistence.storage.utils.EntityGenerator;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.TO_DO;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTask;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskBuilder;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskStatusBuilder;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskTypeBuilder;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TaskRepositoryTest {

    private static final int TASKS_LIMIT = 32;
    private static final int TASKS_BOARDS_LIMIT = 8;

    @Autowired private TaskRepository repository;
    @Autowired private TasksBoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
    }

    @Test
    void findAll_boardIdIsFake() {
        long fakeBoardId = randomPositiveLong();

        assertThatThrownBy(() -> repository.findAllForBoard(fakeBoardId))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The boardId not found in the storage.");
    }

    @Test
    void findAll() {
        TasksBoard board = insertBoard(randomTasksBoard());
        List<Task> expected = insertRandomTasks(board);

        List<Task> tasks = repository.findAllForBoard(board.getEntityId());

        assertThat(tasks).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void findAll_manyBoards() {
        List<TasksBoard> boards = Stream.generate(EntityGenerator::randomTasksBoard)
                .limit(TASKS_BOARDS_LIMIT)
                .map(this::insertBoard)
                .toList();

        List<List<Task>> expectedList = boards.stream()
                .map(this::insertRandomTasks)
                .toList();

        for (int i = 0; i < boards.size(); i++) {
            List<Task> tasks = repository.findAllForBoard(boards.get(i).getEntityId());
            assertThat(tasks).containsExactlyInAnyOrderElementsOf(expectedList.get(i));
        }
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        Optional<Task> opt = repository.findByEntityId(fakeId);
        assertThat(opt).isNotPresent();
    }

    @Test
    void findByEntityId_found() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();
        Task expected = insert(created);

        Optional<Task> opt = repository.findByEntityId(expected.getEntityId());

        assertThat(opt)
                .isPresent()
                .contains(expected);
    }

    @Test
    void findSoftlyByName_boardIdIsFake() {
        String name = randomString();
        long fakeBoardId = randomPositiveLong();

        assertThatThrownBy(() -> repository.findSoftlyByName(name, fakeBoardId))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage( "The boardId not found in the storage.");
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

        assertThat(actualTasks)
                .doesNotContain(savedTask)
                .containsExactlyInAnyOrderElementsOf(savedTasks);
    }

    @Test
    void save_boardIsFake() {
        TasksBoard fakeBoard = randomTasksBoard();
        Task created = randomTaskBuilder().setBoard(fakeBoard).build();

        assertThatThrownBy(() -> repository.save(created))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The boardId not found in the storage.");
    }

    @Test
    void save_typeIsFake() {
        TasksBoard board = insertBoard(randomTasksBoard());
        TaskType fakeType = randomTaskTypeBuilder().build();
        Task created = randomTaskBuilder().setBoard(board).setType(fakeType).build();

        assertThatThrownBy(() -> repository.save(created))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The typeId not found in the storage.");
    }

    @Test
    void save_numberHasIncreasedWithinOneBoardId() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();

        Task actual1 = repository.save(created);
        Task actual2 = repository.save(created);

        assertThat(actual2.getNumber()).isEqualTo(actual1.getNumber() + 1);
    }

    @Test
    void save() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task expected = randomTaskBuilder().setBoard(board).build();

        Task actual = repository.save(expected);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getStatus()).isEqualTo(TO_DO);
        assertThat(actual.getBoard()).isEqualTo(board);
        assertThat(actual.getType()).isEqualTo(expected.getType());
    }

    @Test
    void save_descriptionIsNull() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task expected = randomTaskBuilder().setBoard(board).setDescription(null).build();

        Task actual = repository.save(expected);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isNull();
        assertThat(actual.getStatus()).isEqualTo(TO_DO);
        assertThat(actual.getBoard()).isEqualTo(board);
        assertThat(actual.getType()).isEqualTo(expected.getType());
    }

    @Test
    void update_entityIdIsFake() {
        Task created = randomTask();

        assertThatThrownBy(() -> repository.update(created))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The entityId not found in the storage.");
    }

    @Test
    void update_statusIdIsFake() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);
        Task updated = randomTaskBuilder()
                .setEntityId(saved.getEntityId())
                .setStatus(randomTaskStatusBuilder().build())
                .build();

        assertThatThrownBy(() -> repository.update(updated))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The statusId not found in the storage.");
    }

    @Test
    void update_typeIdIsFake() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created = randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);
        Task updated = randomTaskBuilder()
                .setEntityId(saved.getEntityId())
                .setType(randomTaskTypeBuilder().build())
                .build();

        assertThatThrownBy(() -> repository.update(updated))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The typeId not found in the storage.");
    }

    @Test
    void update() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created =  randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);

        Task updated = randomTaskBuilder().setEntityId(saved.getEntityId()).build();
        Task actual = repository.update(updated);

        assertThat(actual.getEntityId()).isEqualTo(saved.getEntityId());
        assertThat(actual.getName()).isEqualTo(updated.getName());
        assertThat(actual.getCreatedDate()).isEqualTo(saved.getCreatedDate());
        assertThat(actual.getLastModifiedDate()).isNotEqualTo(saved.getLastModifiedDate());
        assertThat(actual.getStatus()).isEqualTo(updated.getStatus());
        assertThat(actual.getBoard()).isEqualTo(saved.getBoard());
        assertThat(actual.getType()).isEqualTo(updated.getType());
        assertThat(actual.getDescription()).isEqualTo(updated.getDescription());
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        assertThat(repository.deleteByEntityId(fakeId)).isFalse();
    }

    @Test
    void deleteByEntityId_found() {
        TasksBoard board = insertBoard(randomTasksBoard());
        Task created =  randomTaskBuilder().setBoard(board).build();
        Task saved = insert(created);

        assertThat(repository.deleteByEntityId(saved.getEntityId())).isTrue();
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
