package rgo.tt.task.rest.api;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction;
import org.junit.platform.commons.util.ExceptionUtils;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.validator.ValidateException;

import static rgo.tt.common.rest.api.utils.RestUtils.mapToHttp;

public class ExceptionCommonHandler implements ExceptionHandlerFunction {

    @Override
    public HttpResponse handleException(ServiceRequestContext ctx, HttpRequest req, Throwable cause) {
        if (cause instanceof ValidateException e) {
            Response response = ErrorResponse.invalidRq(e.getMessage());
            return mapToHttp(response);
        }

        if (cause instanceof InvalidEntityException e) {
            Response response = ErrorResponse.invalidEntity(e.getMessage());
            return mapToHttp(response);
        }

        Response response = ErrorResponse.error(ExceptionUtils.readStackTrace(cause));
        return mapToHttp(response);
    }
}
