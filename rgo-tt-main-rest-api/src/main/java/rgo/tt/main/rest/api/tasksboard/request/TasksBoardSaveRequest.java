package rgo.tt.main.rest.api.tasksboard.request;

public class TasksBoardSaveRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TasksBoardSaveRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
