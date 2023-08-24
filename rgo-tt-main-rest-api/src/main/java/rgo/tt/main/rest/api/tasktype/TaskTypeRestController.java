package rgo.tt.main.rest.api.tasktype;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.tt.common.rest.api.Response;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.rest.api.tasktype.response.TaskTypeGetListResponse;
import rgo.tt.main.service.tasktype.TaskTypeService;

import java.util.List;

import static rgo.tt.common.rest.api.RestUtils.convertToResponseEntity;

@RestController
@RequestMapping(TaskTypeRestController.BASE_URL)
public class TaskTypeRestController implements TaskTypeController {

    public static final String BASE_URL = "/types";

    private final TaskTypeService service;

    public TaskTypeRestController(TaskTypeService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> findAll() {
        List<TaskType> types = service.findAll();
        Response response = TaskTypeGetListResponse.success(types);
        return convertToResponseEntity(response);
    }
}
