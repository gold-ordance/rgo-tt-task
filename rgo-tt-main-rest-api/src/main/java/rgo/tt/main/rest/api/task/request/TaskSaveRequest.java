package rgo.tt.main.rest.api.task.request;

public class TaskSaveRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskPostRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
