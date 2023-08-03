package rgo.tt.main.rest.api.task.request;

import static rgo.tt.common.utils.HelperUtils.size;

public class TaskSaveRequest {

    private String name;
    private Long boardId;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskSaveRequest{" +
                "name='" + name + '\'' +
                ", boardId=" + boardId +
                ", description=" + size(description) +
                '}';
    }
}
