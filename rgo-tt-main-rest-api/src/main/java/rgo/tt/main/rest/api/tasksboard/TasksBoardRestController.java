package rgo.tt.main.rest.api.tasksboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.SuccessResponse;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetEntityResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetListResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardModifyResponse;
import rgo.tt.main.service.tasksboard.TasksBoardService;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.rest.api.utils.RestUtils.DIGITS_PATTERN;
import static rgo.tt.common.rest.api.utils.RestUtils.convertToResponseEntity;
import static rgo.tt.main.rest.api.tasksboard.TasksBoardMapper.map;

@RestController
@RequestMapping(TasksBoardRestController.BASE_URL)
public class TasksBoardRestController implements TasksBoardController {

    public static final String BASE_URL = "/tasks-board";

    private final TasksBoardService service;

    public TasksBoardRestController(TasksBoardService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> findAll() {
        List<TasksBoard> boards = service.findAll();
        Response response = TasksBoardGetListResponse.success(boards);
        return convertToResponseEntity(response);
    }

    @Override
    @GetMapping("/{entityId:" + DIGITS_PATTERN + "}")
    public ResponseEntity<Response> findByEntityId(@PathVariable Long entityId) {
        Optional<TasksBoard> board = service.findByEntityId(entityId);
        Response response = TasksBoardGetEntityResponse.from(board);
        return convertToResponseEntity(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<Response> save(@RequestBody TasksBoardSaveRequest rq) {
        TasksBoard board = service.save(map(rq));
        Response response = TasksBoardModifyResponse.saved(board);
        return convertToResponseEntity(response);
    }

    @Override
    @DeleteMapping("/{entityId:" + DIGITS_PATTERN + "}")
    public ResponseEntity<Response> deleteByEntityId(@PathVariable Long entityId) {
        boolean isDeleted = service.deleteByEntityId(entityId);
        Response response = Response.from(isDeleted);
        return convertToResponseEntity(response);
    }
}
