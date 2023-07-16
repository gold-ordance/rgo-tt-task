package rgo.tt.main.persistence.storage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Serializable {

    private final Long entityId;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public Task(Builder builder) {
        entityId = builder.entityId;
        name = builder.name;
        createdDate = builder.createdDate;
        lastModifiedDate = builder.lastModifiedDate;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(entityId, task.entityId)
                && Objects.equals(name, task.name)
                && Objects.equals(createdDate, task.createdDate)
                && Objects.equals(lastModifiedDate, task.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name, createdDate, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long entityId;
        private String name;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public Builder setEntityId(Long entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setLastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
