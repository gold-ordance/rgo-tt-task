package rgo.tt.task.rest.api.tasksboard;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.task.rest.api.AbstractRateLimiterTest;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTasksBoardServiceTest extends AbstractRateLimiterTest {

    private static final String BASE_URL = "/tasks-boards";

    @Test
    void findAll() {
        execute(() ->
                CLIENT.get(BASE_URL)
                        .aggregate()
                        .join()
                        .status()
                        .code());
    }

    @Test
    void findByEntityId() {
        long entityId = randomPositiveLong();
        execute(() ->
                CLIENT.get(BASE_URL + "/" + entityId)
                        .aggregate()
                        .join()
                        .status()
                        .code());
    }

    @Test
    void save() {
        execute(() ->
                CLIENT.post(BASE_URL, Strings.EMPTY)
                        .aggregate()
                        .join()
                        .status()
                        .code());
    }

    @Test
    void deleteByEntityId() {
        long entityId = randomPositiveLong();
        execute(() ->
                CLIENT.delete(BASE_URL + "/" + entityId)
                        .aggregate()
                        .join()
                        .status()
                        .code());
    }
}
