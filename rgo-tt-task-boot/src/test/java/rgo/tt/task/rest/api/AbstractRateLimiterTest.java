package rgo.tt.task.rest.api;

import com.linecorp.armeria.client.WebClient;
import rgo.tt.common.rest.api.StatusCode;

import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractRateLimiterTest {

    private static final int PORT = 8080;
    private static final String HOST = "http://127.0.0.1:" + PORT;
    protected static final WebClient CLIENT = WebClient.of(HOST);
    private static final int NUMBER_OF_RETRY_REQUESTS = 10;

    protected void execute(Supplier<Integer> supplier) {
        boolean isContainsTooManyRequests = IntStream.range(0, NUMBER_OF_RETRY_REQUESTS)
                .mapToObj(ignored -> supplier.get())
                .anyMatch(code -> code == StatusCode.TOO_MANY_REQUESTS.getHttpCode());

        assertThat(isContainsTooManyRequests).isTrue();
    }
}
