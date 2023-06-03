package rgo.tt.main.persistence;

import rgo.tt.main.persistence.storage.entity.Task;

import static rgo.tt.main.common.utils.CommonUtils.randomString;

public final class EntityGenerator {

    private EntityGenerator() {
    }

    public static Task randomTask() {
        return Task.builder()
                .setName(randomString())
                .build();
    }

    public static Task randomTask(Long entityId) {
        return Task.builder()
                .setEntityId(entityId)
                .setName(randomString())
                .build();
    }

    public static Task randomTask(String name) {
        return Task.builder()
                .setName(name)
                .build();
    }
}
