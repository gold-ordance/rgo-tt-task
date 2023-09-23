package rgo.tt.task.rest.api.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;
import rgo.tt.task.rest.api.taskstatus.dto.TaskStatusDto;
import rgo.tt.task.rest.api.tasktype.dto.TaskTypeDto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskDto implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private Long entityId;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String description;
    private Long number;
    private TasksBoardDto board;
    private TaskTypeDto type;
    private TaskStatusDto status;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public TasksBoardDto getBoard() {
        return board;
    }

    public void setBoard(TasksBoardDto board) {
        this.board = board;
    }

    public TaskTypeDto getType() {
        return type;
    }

    public void setType(TaskTypeDto type) {
        this.type = type;
    }

    public TaskStatusDto getStatus() {
        return status;
    }

    public void setStatus(TaskStatusDto status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(entityId, taskDto.entityId)
                && Objects.equals(name, taskDto.name)
                && Objects.equals(createdDate, taskDto.createdDate)
                && Objects.equals(lastModifiedDate, taskDto.lastModifiedDate)
                && Objects.equals(description, taskDto.description)
                && Objects.equals(number, taskDto.number)
                && Objects.equals(board, taskDto.board)
                && Objects.equals(type, taskDto.type)
                && Objects.equals(status, taskDto.status);
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
        return "TaskDto{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description='" + description + '\'' +
                ", number=" + number +
                ", board=" + board +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
