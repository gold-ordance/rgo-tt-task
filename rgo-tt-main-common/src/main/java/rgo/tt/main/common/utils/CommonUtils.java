package rgo.tt.main.common.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static Long randomPositiveLong() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }
}
