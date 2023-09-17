package rgo.tt.task.rest.api.taskstatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.rest.api.taskstatus.response.TaskStatusGetListResponse;

@Tag(name = "TaskStatus", description = "Interaction with task statuses")
public interface TaskStatusController {

    @Operation(summary = "Find all task statuses")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving of all task statuses",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskStatusGetListResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal unexpected error",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            )
    })
    ResponseEntity<Response> findAll();
}
