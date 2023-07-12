package rgo.tt.main.rest.api.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.SuccessResponse;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.response.TaskGetEntityResponse;
import rgo.tt.main.rest.api.task.response.TaskGetListResponse;
import rgo.tt.main.rest.api.task.response.TaskModifyResponse;
import rgo.tt.main.service.task.TaskService;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.rest.api.RestUtils.DIGITS_PATTERN;
import static rgo.tt.common.rest.api.RestUtils.convert;
import static rgo.tt.main.rest.api.task.TaskMapper.map;

@RestController
@RequestMapping("/tasks")
public class TaskRestController implements TaskController {

    private final TaskService service;

    public TaskRestController(TaskService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        List<Task> tasks = service.findAll();
        Response response = TaskGetListResponse.success(tasks);
        return convert(response);
    }

    @Override
    @GetMapping("/{entityId:" + DIGITS_PATTERN + "}")
    public ResponseEntity<Response> getByEntityId(@PathVariable Long entityId) {
        Optional<Task> task = service.findByEntityId(entityId);
        Response response = task.isPresent()
                ? TaskGetEntityResponse.success(task.get())
                : ErrorResponse.notFound();

        return convert(response);
    }

    @Override
    @GetMapping(params = "name")
    public ResponseEntity<Response> getByName(String name) {
        List<Task> tasks = service.findSoftlyByName(name);
        Response response = TaskGetListResponse.success(tasks);
        return convert(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<Response> save(@RequestBody TaskSaveRequest rq) {
        Task stored = service.save(map(rq));
        Response response = TaskModifyResponse.saved(stored);
        return convert(response);
    }

    @Override
    @PutMapping
    public ResponseEntity<Response> put(@RequestBody TaskPutRequest rq) {
        Task updated = service.update(map(rq));
        Response response = TaskModifyResponse.updated(updated);
        return convert(response);
    }

    @Override
    @DeleteMapping("/{entityId:" + DIGITS_PATTERN + "}")
    public ResponseEntity<Response> deleteByEntityId(@PathVariable Long entityId) {
        boolean deleted = service.deleteByEntityId(entityId);
        Response response = deleted
                ? SuccessResponse.noContent()
                : ErrorResponse.notFound();

        return convert(response);
    }
}
