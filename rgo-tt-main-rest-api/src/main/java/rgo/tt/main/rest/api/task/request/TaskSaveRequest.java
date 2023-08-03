package rgo.tt.main.rest.api.task.request;

public class TaskSaveRequest {

    private String name;
    private Long boardId;

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

    @Override
    public String toString() {
        return "TaskSaveRequest{" +
                "name='" + name + '\'' +
                ", boardId=" + boardId +
                '}';
    }
}
