package rgo.tt.main.rest.api.tasksboard.request;

public class TasksBoardPutRequest {

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
        return "TasksBoardPutRequest{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                '}';
    }
}
