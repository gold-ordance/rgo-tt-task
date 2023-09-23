package rgo.tt.task.persistence.storage.entity;

import java.util.Objects;

public class TasksBoard {

    private final Long entityId;
    private final String name;
    private final String shortName;

    private TasksBoard(Builder builder) {
        entityId = builder.entityId;
        name = builder.name;
        shortName = builder.shortName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksBoard that = (TasksBoard) o;
        return Objects.equals(entityId, that.entityId)
                && Objects.equals(name, that.name)
                && Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId,
                name,
                shortName);
    }

    @Override
    public String toString() {
        return "TasksBoard{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long entityId;
        private String name;
        private String shortName;

        public Builder setEntityId(Long entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setShortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public TasksBoard build() {
            return new TasksBoard(this);
        }
    }
}
