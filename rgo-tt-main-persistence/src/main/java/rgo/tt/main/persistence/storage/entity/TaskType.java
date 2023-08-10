package rgo.tt.main.persistence.storage.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TaskType implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String name;

    private TaskType(TaskType.Builder builder) {
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
        TaskType that = (TaskType) o;
        return Objects.equals(entityId, that.entityId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name);
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                '}';
    }

    public static TaskType.Builder builder() {
        return new TaskType.Builder();
    }

    public static class Builder {

        private Long entityId;
        private String name;

        public TaskType.Builder setEntityId(Long entityId) {
            this.entityId = entityId;
            return this;
        }

        public TaskType.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public TaskType build() {
            return new TaskType(this);
        }
    }
}
