package rgo.tt.main.rest.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.common.validator.ValidateException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rgo.tt.common.utils.RandomUtils.randomString;

class ExceptionCommonHandlerTest {

    private ExceptionCommonHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ExceptionCommonHandler();
    }

    @Test
    void handle_validateException() {
        String msg = randomString();
        ValidateException e = new ValidateException(msg);

        ResponseEntity<Response> responseEntity = handler.handle(e);
        Response response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(msg, response.getStatus().getMessage());
        assertEquals(StatusCode.INVALID_RQ, response.getStatus().getStatusCode());
    }

    @Test
    void handle_invalidEntityException() {
        String msg = randomString();
        InvalidEntityException e = new InvalidEntityException(msg);

        ResponseEntity<Response> responseEntity = handler.handle(e);
        Response response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(msg, response.getStatus().getMessage());
        assertEquals(StatusCode.INVALID_ENTITY, response.getStatus().getStatusCode());
    }

    @Test
    void handle_exception() {
        String msg = randomString();
        Exception e = new Exception(msg);

        ResponseEntity<Response> responseEntity = handler.handle(e);
        Response response = responseEntity.getBody();

        assertNotNull(response);
        assertTrue(response.getStatus().getMessage().contains(msg));
        assertEquals(StatusCode.ERROR, response.getStatus().getStatusCode());
    }
}
