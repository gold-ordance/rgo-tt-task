package rgo.tt.main.rest.api.tasksboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.SuccessResponse;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardPutRequest;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetEntityResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetListResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardModifyResponse;
import rgo.tt.main.service.tasksboard.TasksBoardService;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.rest.api.RestUtils.DIGITS_PATTERN;
import static rgo.tt.common.rest.api.RestUtils.convert;
import static rgo.tt.main.rest.api.tasksboard.response.TasksBoardMapper.map;

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
    public ResponseEntity<Response> getAll() {
        List<TasksBoard> boards = service.findAll();
        Response response = TasksBoardGetListResponse.success(boards);
        return convert(response);
    }

    @Override
    @GetMapping("/{entityId:" + DIGITS_PATTERN + "}")
    public ResponseEntity<Response> getByEntityId(@PathVariable Long entityId) {
        Optional<TasksBoard> board = service.findByEntityId(entityId);
        Response response = board.isPresent()
                ? TasksBoardGetEntityResponse.success(board.get())
                : ErrorResponse.notFound();

        return convert(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<Response> save(@RequestBody TasksBoardSaveRequest rq) {
        TasksBoard board = service.save(map(rq));
        Response response = TasksBoardModifyResponse.saved(board);
        return convert(response);
    }

    @Override
    @PutMapping
    public ResponseEntity<Response> put(@RequestBody TasksBoardPutRequest rq) {
        TasksBoard board = service.update(map(rq));
        Response response = TasksBoardModifyResponse.updated(board);
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
