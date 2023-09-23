package rgo.tt.task.persistence.storage.repository.tasksboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.task.persistence.config.PersistenceConfig;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.persistence.storage.utils.EntityGenerator;
import rgo.tt.task.persistence.storage.utils.H2PersistenceUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTasksBoard;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class TasksBoardRepositoryTest {

    private static final int TASKS_BOARDS_LIMIT = 32;

    @Autowired private TasksBoardRepository repository;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
    }

    @Test
    void findAll() {
        List<TasksBoard> expected = insertRandomBoards();

        List<TasksBoard> actual = repository.findAll();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();

        Optional<TasksBoard> actual = repository.findByEntityId(fakeId);
        assertThat(actual).isNotPresent();
    }

    @Test
    void findByEntityId_found() {
        TasksBoard created = randomTasksBoard();
        TasksBoard expected = insert(created);

        Optional<TasksBoard> actual = repository.findByEntityId(expected.getEntityId());
        assertThat(actual)
                .isPresent()
                .contains(expected);
    }

    @Test
    void save() {
        TasksBoard expected = randomTasksBoard();

        TasksBoard actual = repository.save(expected);
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void deleteByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        boolean isDeleted = repository.deleteByEntityId(fakeId);
        assertThat(isDeleted).isFalse();
    }

    @Test
    void deleteByEntityId_found() {
        TasksBoard created =  randomTasksBoard();
        TasksBoard saved = insert(created);

        boolean isDeleted = repository.deleteByEntityId(saved.getEntityId());
        assertThat(isDeleted).isTrue();
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
