package rgo.tt.main.rest.api.tasksboard;

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
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardPutRequest;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetEntityResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardGetListResponse;
import rgo.tt.main.rest.api.tasksboard.response.TasksBoardModifyResponse;

@Tag(name = "TasksBoard", description = "Interaction with tasks boards")
public interface TasksBoardController {

    @Operation(summary = "Get all tasks boards")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving of all boards",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TasksBoardGetListResponse.class))
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
    ResponseEntity<Response> getAll();

    @Operation(summary = "Get tasks board by entityId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful receiving board by entityId",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TasksBoardGetEntityResponse.class))
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
    ResponseEntity<Response> getByEntityId(Long entityId);

    @Operation(summary = "Save board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully saving the board",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TasksBoardModifyResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The name parameter is empty",
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
    ResponseEntity<Response> save(TasksBoardSaveRequest rq);

    @Operation(summary = "Update board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updating the board",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TasksBoardModifyResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The entityId parameter is (null/negative) or the name parameter is (null/empty)",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "The entityId not found in the storage.",
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
    ResponseEntity<Response> put(TasksBoardPutRequest rq);

    @Operation(summary = "Delete board by entityId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful deleting board by entityId",
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