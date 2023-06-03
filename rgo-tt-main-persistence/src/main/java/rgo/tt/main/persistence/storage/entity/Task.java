package rgo.tt.main.persistence.storage.entity;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {

    private final Long entityId;
    private final String name;

    public Task(Builder builder) {
        this.entityId = builder.entityId;
        this.name = builder.name;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(entityId, task.entityId) && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name);
    }

    @Override
    public String toString() {
        return "Task{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long entityId;
        private String name;

        public Builder setEntityId(Long entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
