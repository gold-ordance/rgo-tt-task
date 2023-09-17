package rgo.tt.task.rest.api.task;

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
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;
import rgo.tt.task.rest.api.task.response.TaskGetEntityResponse;
import rgo.tt.task.rest.api.task.response.TaskGetListResponse;
import rgo.tt.task.rest.api.task.response.TaskModifyResponse;

@Tag(name = "Task", description = "Interaction with tasks")
interface TaskController {

    @Operation(summary = "Find all tasks for the board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving of all tasks",
                    content = {
                        @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = TaskGetListResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The boardId parameter is negative",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "The boardId not found in the storage.",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> findAllForBoard(Long boardId);

    @Operation(summary = "Find task by entityId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving task by entityId",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskGetEntityResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found by entityId",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> findByEntityId(Long entityId);

    @Operation(summary = "Find tasks by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving of tasks by name",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskGetListResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The name parameter is empty or the boardId parameter is (empty/negative)",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "The boardId not found in the storage.",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> findByName(String name, Long boardId);

    @Operation(summary = "Save task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully saving the task",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskModifyResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The name parameter is empty or the (boardId/typeId) parameter is (empty/negative)",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "The (boardId/typeId) not found in the storage.",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> save(TaskSaveRequest rq);

    @Operation(summary = "Update task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updating the task",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskModifyResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The (entityId/typeId/statusId) parameter is (null/negative) or the name parameter is (null/empty)",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "The (entityId/typeId/statusId) not found in the storage.",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> update(TaskPutRequest rq);

    @Operation(summary = "Delete task by entityId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful deleting task by entityId",
                    content = {
                            @Content(schema = @Schema)
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found by entityId",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
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
    ResponseEntity<Response> deleteByEntityId(Long entityId);
}
