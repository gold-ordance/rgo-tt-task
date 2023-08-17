package rgo.tt.main.persistence.storage.utils;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;

import static rgo.tt.common.utils.RandomUtils.randomBigString;
import static rgo.tt.common.utils.RandomUtils.randomElement;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomShortString;
import static rgo.tt.common.utils.RandomUtils.randomString;

public final class EntityGenerator {

    public static final TaskStatus TO_DO = TaskStatus.builder().setEntityId(1L).setName("TO DO").build();
    public static final TaskStatus IN_PROGRESS = TaskStatus.builder().setEntityId(2L).setName("IN PROGRESS").build();
    public static final TaskStatus DONE = TaskStatus.builder().setEntityId(3L).setName("DONE").build();

    public static final List<TaskStatus> STATUSES = List.of(TO_DO, IN_PROGRESS, DONE);

    public static final TaskType TASK = TaskType.builder().setEntityId(1L).setName("Task").build();
    public static final TaskType BUG = TaskType.builder().setEntityId(2L).setName("Bug").build();

    public static final List<TaskType> TYPES = List.of(TASK, BUG);

    private EntityGenerator() {
    }

    public static Task randomTask() {
        return randomTaskBuilder().build();
    }

    public static Task.Builder randomTaskBuilder() {
        return Task.builder()
                .setEntityId(randomPositiveLong())
                .setName(randomString())
                .setBoard(randomTasksBoard())
                .setType(randomElement(TYPES))
                .setStatus(randomElement(STATUSES))
                .setDescription(randomBigString());
    }

    public static TaskStatus randomTaskStatus() {
        return randomElement(STATUSES);
    }

    public static TaskStatus.Builder randomTaskStatusBuilder() {
        return TaskStatus.builder()
                .setEntityId(randomPositiveLong())
                .setName(randomString());
    }

    public static TasksBoard randomTasksBoard() {
        return randomTasksBoardBuilder().build();
    }

    public static TasksBoard.Builder randomTasksBoardBuilder() {
        return TasksBoard.builder()
                .setEntityId(randomPositiveLong())
                .setName(randomString())
                .setShortName(randomShortString());
    }

    public static TaskType randomTaskType() {
        return randomElement(TYPES);
    }

    public static TaskType.Builder randomTaskTypeBuilder() {
        return TaskType.builder()
                .setEntityId(randomPositiveLong())
                .setName(randomString());
    }
}
