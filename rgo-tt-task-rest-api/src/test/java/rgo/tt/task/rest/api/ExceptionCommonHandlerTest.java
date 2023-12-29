package rgo.tt.task.rest.api;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.ServiceRequestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.ValidateException;
import rgo.tt.common.rest.api.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.StatusCode.ERROR;
import static rgo.tt.common.rest.api.StatusCode.INVALID_ENTITY;
import static rgo.tt.common.rest.api.StatusCode.INVALID_RQ;
import static rgo.tt.common.armeria.rest.RestUtils.fromJson;
import static rgo.tt.common.utils.RandomUtils.randomString;

@ExtendWith(MockitoExtension.class)
class ExceptionCommonHandlerTest {

    private final ExceptionCommonHandler handler = new ExceptionCommonHandler();

    @Mock private ServiceRequestContext ctx;
    @Mock private HttpRequest req;

    @Test
    void handleException_validateException() {
        String errorMsg = randomString();
        ValidateException e = new ValidateException(errorMsg);

        ErrorResponse response = handle(e);

        assertThat(response.getStatus().getMessage()).isEqualTo(errorMsg);
        assertThat(response.getStatus().getStatusCode()).isEqualTo(INVALID_RQ);
    }

    @Test
    void handleException_invalidEntityException() {
        String errorMsg = randomString();
        InvalidEntityException e = new InvalidEntityException(errorMsg);

        ErrorResponse response = handle(e);

        assertThat(response.getStatus().getMessage()).isEqualTo(errorMsg);
        assertThat(response.getStatus().getStatusCode()).isEqualTo(INVALID_ENTITY);
    }

    @Test
    void handleException_undefinedException() {
        UndefinedException e = new UndefinedException();

        ErrorResponse response = handle(e);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(ERROR);
    }

    private ErrorResponse handle(Exception e) {
        HttpResponse httpResponse = handler.handleException(ctx, req, e);
        String json = httpResponse.aggregate().join().contentUtf8();
        return fromJson(json, ErrorResponse.class);
    }

    private static class UndefinedException extends RuntimeException {
    }
}
