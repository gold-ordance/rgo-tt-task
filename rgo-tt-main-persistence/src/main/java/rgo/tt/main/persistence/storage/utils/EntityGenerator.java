package rgo.tt.main.persistence.storage.utils;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;

import static rgo.tt.common.utils.RandomUtils.*;

public final class EntityGenerator {

    public static final TaskStatus TO_DO = TaskStatus.builder().setEntityId(1L).setName("TO DO").build();
    public static final TaskStatus IN_PROGRESS = TaskStatus.builder().setEntityId(2L).setName("IN PROGRESS").build();
    public static final TaskStatus DONE = TaskStatus.builder().setEntityId(3L).setName("DONE").build();

    public static final List<TaskStatus> STATUSES = List.of(TO_DO, IN_PROGRESS, DONE);

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
                .setName(randomString());
    }
}
