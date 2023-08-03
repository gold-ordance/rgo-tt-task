package rgo.tt.main.persistence.storage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static rgo.tt.common.utils.HelperUtils.size;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final TasksBoard board;
    private final TaskStatus status;
    private final String description;

    public Task(Builder builder) {
        entityId = builder.entityId;
        name = builder.name;
        createdDate = builder.createdDate;
        lastModifiedDate = builder.lastModifiedDate;
        board = builder.board;
        status = builder.status;
        description = builder.description;
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

    public TasksBoard getBoard() {
        return board;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(entityId, task.entityId)
                && Objects.equals(name, task.name)
                && Objects.equals(createdDate, task.createdDate)
                && Objects.equals(lastModifiedDate, task.lastModifiedDate)
                && Objects.equals(board, task.board)
                && Objects.equals(status, task.status)
                && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name, createdDate, lastModifiedDate, board, status, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", board=" + board +
                ", status=" + status +
                ", description=" + size(description) +
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
        private TasksBoard board;
        private TaskStatus status;
        private String description;

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

        public Builder setBoard(TasksBoard board) {
            this.board = board;
            return this;
        }

        public Builder setStatus(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
