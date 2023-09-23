package rgo.tt.task.rest.api.tasktype.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TaskTypeDto implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private Long entityId;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskTypeDto that = (TaskTypeDto) o;
        return Objects.equals(entityId, that.entityId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name);
    }

    @Override
    public String toString() {
        return "TaskTypeDto{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                '}';
    }
}
