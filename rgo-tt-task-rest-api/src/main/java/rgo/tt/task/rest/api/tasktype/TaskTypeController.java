package rgo.tt.task.rest.api.tasktype;

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
import rgo.tt.task.rest.api.tasktype.response.TaskTypeGetListResponse;

@Tag(name = "TaskType", description = "Interaction with task types")
public interface TaskTypeController {

    @Operation(summary = "Find all task types")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving of all task types",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskTypeGetListResponse.class))
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
