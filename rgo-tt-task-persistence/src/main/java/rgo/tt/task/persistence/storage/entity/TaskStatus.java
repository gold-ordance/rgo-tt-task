package rgo.tt.task.persistence.storage.entity;

import java.util.Objects;

public class TaskStatus {

    private final Long entityId;
    private final String name;

    private TaskStatus(Builder builder) {
        entityId = builder.entityId;
        name = builder.name;
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
        TaskStatus that = (TaskStatus) o;
        return Objects.equals(entityId, that.entityId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name);
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
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

        public TaskStatus build() {
            return new TaskStatus(this);
        }
    }
}
