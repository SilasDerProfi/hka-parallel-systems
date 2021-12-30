package parasys.blockingqueuestore;

import java.util.Arrays;

public final class Logger {

    private Logger() {
    }

    public static void log(String task, Object... affectedThings) {
        System.out.printf("%-15s%s\r\n", task, Arrays.toString(affectedThings));
    }
}
