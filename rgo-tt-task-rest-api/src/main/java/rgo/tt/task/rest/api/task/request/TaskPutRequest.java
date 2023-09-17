package rgo.tt.task.rest.api.task.request;

import static org.apache.commons.lang3.StringUtils.strip;
import static rgo.tt.common.utils.HelperUtils.size;

public class TaskPutRequest {

    private Long entityId;
    private String name;
    private String description;
    private Long typeId;
    private Long statusId;

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
        this.name = strip(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = strip(description);
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "TaskPutRequest{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", description=" + size(description) +
                ", typeId=" + typeId +
                ", statusId=" + statusId +
                '}';
    }
}
