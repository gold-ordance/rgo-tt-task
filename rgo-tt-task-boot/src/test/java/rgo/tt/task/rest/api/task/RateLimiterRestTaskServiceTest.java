package rgo.tt.task.rest.api.task;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.task.rest.api.AbstractRateLimiterTest;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestTaskServiceTest extends AbstractRateLimiterTest {

    private static final String BASE_URL = "/tasks";

    @Test
    void findAllForBoard() {
        long boardId = randomPositiveLong();
        execute(() ->
                CLIENT.get(BASE_URL + "?boardId=" + boardId)
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
    void findByName() {
        String name = randomString();
        long boardId = randomPositiveLong();

        execute(() ->
                CLIENT.get(BASE_URL + "?name=" + name + "&boardId=" + boardId)
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
    void update() {
        execute(() ->
                CLIENT.put(BASE_URL, Strings.EMPTY)
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
