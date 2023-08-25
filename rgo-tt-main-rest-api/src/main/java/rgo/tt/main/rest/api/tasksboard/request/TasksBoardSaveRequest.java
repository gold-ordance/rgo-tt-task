package rgo.tt.main.rest.api.tasksboard.request;

import static org.apache.commons.lang3.StringUtils.strip;

public class TasksBoardSaveRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = strip(name);
    }

    @Override
    public String toString() {
        return "TasksBoardSaveRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
