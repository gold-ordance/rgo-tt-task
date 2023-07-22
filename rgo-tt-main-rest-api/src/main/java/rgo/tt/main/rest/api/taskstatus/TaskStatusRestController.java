package rgo.tt.main.rest.api.taskstatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.tt.common.rest.api.Response;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.rest.api.taskstatus.response.TaskStatusGetListResponse;
import rgo.tt.main.service.taskstatus.TaskStatusService;

import java.util.List;

import static rgo.tt.common.rest.api.RestUtils.convert;

@RestController
@RequestMapping("/statuses")
public class TaskStatusRestController implements TaskStatusController {

    private final TaskStatusService service;

    public TaskStatusRestController(TaskStatusService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        List<TaskStatus> statuses = service.findAll();
        Response response = TaskStatusGetListResponse.success(statuses);
        return convert(response);
    }
}
