package rgo.tt.main.persistence.storage.utils;

import rgo.tt.main.persistence.storage.entity.Task;

import static rgo.tt.common.utils.RandomUtils.randomString;

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
