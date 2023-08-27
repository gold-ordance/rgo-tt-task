package rgo.tt.main.rest.api;

import org.junit.platform.commons.util.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.validator.ValidateException;

import static rgo.tt.common.rest.api.utils.RestUtils.convertToResponseEntity;

@RestControllerAdvice
public class ExceptionCommonHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<Response> handle(ValidateException e) {
        Response response = ErrorResponse.invalidRq(e.getMessage());
        return convertToResponseEntity(response);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<Response> handle(InvalidEntityException e) {
        Response response = ErrorResponse.invalidEntity(e.getMessage());
        return convertToResponseEntity(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handle(Exception e) {
        Response response = ErrorResponse.error(ExceptionUtils.readStackTrace(e));
        return convertToResponseEntity(response);
    }
}
