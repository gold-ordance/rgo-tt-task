package rgo.tt.task.rest.api.tasksboard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.common.armeria.test.ratelimiter.AbstractRateLimiterTest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTasksBoardServiceTest extends AbstractRateLimiterTest {

    private static final int PORT = 8080;
    private static final String BASE_URL = "/tasks-boards";

    protected RateLimiterRestTasksBoardServiceTest() {
        super(PORT);
    }

    @Test
    void findAll() {
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void findByEntityId() {
        long entityId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL + "/" + entityId));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void save() {
        boolean isRequestRejected = isRequestRejected(() -> post(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void deleteByEntityId() {
        long entityId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> delete(BASE_URL + "/" + entityId));
        assertThat(isRequestRejected).isTrue();
    }
}
