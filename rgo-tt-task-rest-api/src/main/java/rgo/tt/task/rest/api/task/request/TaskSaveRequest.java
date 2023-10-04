package rgo.tt.task.rest.api.task.request;

import static org.apache.commons.lang3.StringUtils.strip;
import static rgo.tt.common.utils.HelperUtils.size;

public class TaskSaveRequest {

    private String name;
    private String description;
    private Long boardId;
    private Long typeId;
    private Long statusId;

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

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
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
        return "TaskSaveRequest{" +
                "name='" + name + '\'' +
                ", description=" + size(description) +
                ", boardId=" + boardId +
                ", typeId=" + typeId +
                ", statusId=" + statusId +
                '}';
    }
}
