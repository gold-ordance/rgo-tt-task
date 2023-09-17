package rgo.tt.task.rest.api.taskstatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.rest.api.taskstatus.response.TaskStatusGetListResponse;
import rgo.tt.task.service.taskstatus.TaskStatusService;

import java.util.List;

import static rgo.tt.common.rest.api.utils.RestUtils.convertToResponseEntity;

@RestController
@RequestMapping(TaskStatusRestController.BASE_URL)
public class TaskStatusRestController implements TaskStatusController {

    public static final String BASE_URL = "/statuses";

    private final TaskStatusService service;

    public TaskStatusRestController(TaskStatusService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> findAll() {
        List<TaskStatus> statuses = service.findAll();
        Response response = TaskStatusGetListResponse.success(statuses);
        return convertToResponseEntity(response);
    }
}
