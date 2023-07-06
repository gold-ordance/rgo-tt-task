package rgo.tt.main.rest.api.task.request;

public class TaskPutRequest {

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
    public String toString() {
        return "TaskPutRequest{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                '}';
    }
}
