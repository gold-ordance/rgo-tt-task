package rgo.tt.main.persistence.storage.utils;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;

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
               .setStatus(randomElement(STATUSES));
    }

    public static TaskStatus randomTaskStatus() {
        return randomElement(STATUSES);
    }

    public static TaskStatus.Builder randomTaskStatusBuilder() {
        return TaskStatus.builder()
              .setEntityId(randomPositiveLong())
              .setName(randomString());
    }
}
