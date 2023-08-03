package rgo.tt.main.rest.api.task.request;

import static rgo.tt.common.utils.HelperUtils.size;

public class TaskPutRequest {

    private Long entityId;
    private String name;
    private Long statusId;
    private String description;

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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskPutRequest{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", statusId=" + statusId +
                ", description=" + size(description) +
                '}';
    }
}
