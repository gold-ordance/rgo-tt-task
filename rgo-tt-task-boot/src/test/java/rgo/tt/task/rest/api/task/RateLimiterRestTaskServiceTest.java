package rgo.tt.task.rest.api.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.common.armeria.test.ratelimiter.AbstractRateLimiterTest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTaskServiceTest extends AbstractRateLimiterTest {

    private static final int PORT = 8080;
    private static final String BASE_URL = "/tasks";

    protected RateLimiterRestTaskServiceTest() {
        super(PORT);
    }

    @Test
    void findAllForBoard() {
        long boardId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL + "?boardId=" + boardId));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void findByEntityId() {
        long entityId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL + "/" + entityId));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void findByName() {
        String name = randomString();
        long boardId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL + "?name=" + name + "&boardId=" + boardId));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void save() {
        boolean isRequestRejected = isRequestRejected(() -> post(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void update() {
        boolean isRequestRejected = isRequestRejected(() -> put(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void deleteByEntityId() {
        long entityId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> delete(BASE_URL + "/" + entityId));
        assertThat(isRequestRejected).isTrue();
    }
}
