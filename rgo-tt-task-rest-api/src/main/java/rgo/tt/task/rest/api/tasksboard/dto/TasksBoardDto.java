package rgo.tt.task.rest.api.tasksboard.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TasksBoardDto implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private Long entityId;
    private String name;
    private String shortName;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksBoardDto that = (TasksBoardDto) o;
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
        return "TasksBoardDto{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
