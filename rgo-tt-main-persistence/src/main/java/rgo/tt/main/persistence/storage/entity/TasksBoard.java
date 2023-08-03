package rgo.tt.main.persistence.storage.entity;

import java.io.Serializable;
import java.util.Objects;

public class TasksBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String name;

    public TasksBoard(Builder builder) {
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
        TasksBoard that = (TasksBoard) o;
        return Objects.equals(entityId, that.entityId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name);
    }

    @Override
    public String toString() {
        return "TasksBoard{" +
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

        public TasksBoard build() {
            return new TasksBoard(this);
        }
    }
}
