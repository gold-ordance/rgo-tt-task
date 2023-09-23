package rgo.tt.task.persistence.storage.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import static rgo.tt.common.utils.HelperUtils.size;

public class Task {

    private final Long entityId;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final String description;
    private final Long number;
    private final TasksBoard board;
    private final TaskType type;
    private final TaskStatus status;

    private Task(Builder builder) {
        entityId = builder.entityId;
        name = builder.name;
        createdDate = builder.createdDate;
        lastModifiedDate = builder.lastModifiedDate;
        description = builder.description;
        number = builder.number;
        board = builder.board;
        type = builder.type;
        status = builder.status;
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

    public String getDescription() {
        return description;
    }

    public Long getNumber() {
        return number;
    }

    public TasksBoard getBoard() {
        return board;
    }

    public TaskType getType() {
        return type;
    }

    public TaskStatus getStatus() {
        return status;
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
                && Objects.equals(description, task.description)
                && Objects.equals(number, task.number)
                && Objects.equals(board, task.board)
                && Objects.equals(type, task.type)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId,
                name,
                createdDate,
                lastModifiedDate,
                description,
                number,
                board,
                type,
                status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description=" + size(description) +
                ", number=" + number +
                ", board=" + board +
                ", type=" + type +
                ", status=" + status +
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
        private String description;
        private Long number;
        private TasksBoard board;
        private TaskType type;
        private TaskStatus status;

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

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setNumber(Long number) {
            this.number = number;
            return this;
        }

        public Builder setBoard(TasksBoard board) {
            this.board = board;
            return this;
        }

        public Builder setType(TaskType type) {
            this.type = type;
            return this;
        }

        public Builder setStatus(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
