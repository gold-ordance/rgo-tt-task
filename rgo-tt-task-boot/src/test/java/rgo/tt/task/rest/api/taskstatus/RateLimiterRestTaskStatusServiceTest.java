package rgo.tt.task.rest.api.taskstatus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.common.armeria.test.ratelimiter.AbstractRestRateLimiterTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTaskStatusServiceTest extends AbstractRestRateLimiterTest {

    private static final int PORT = 8080;
    private static final String BASE_URL = "/statuses";

    protected RateLimiterRestTaskStatusServiceTest() {
        super(PORT);
    }

    @Test
    void findAll() {
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }
}
