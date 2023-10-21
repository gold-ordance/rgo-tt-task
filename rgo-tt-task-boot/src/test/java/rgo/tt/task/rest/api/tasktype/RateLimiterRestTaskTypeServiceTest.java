package rgo.tt.task.rest.api.tasktype;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.common.armeria.test.ratelimiter.AbstractRateLimiterTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTaskTypeServiceTest extends AbstractRateLimiterTest {

    private static final int PORT = 8080;
    private static final String BASE_URL = "/types";

    protected RateLimiterRestTaskTypeServiceTest() {
        super(PORT);
    }

    @Test
    void findAll() {
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }
}
