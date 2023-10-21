package rgo.tt.task.rest.api.taskstatus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.task.rest.api.AbstractRateLimiterTest;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTaskStatusServiceTest extends AbstractRateLimiterTest {

    private static final String BASE_URL = "/statuses";

    @Test
    void findAll() {
        execute(() ->
                CLIENT.get(BASE_URL)
                        .aggregate()
                        .join()
                        .status()
                        .code());
    }
}
