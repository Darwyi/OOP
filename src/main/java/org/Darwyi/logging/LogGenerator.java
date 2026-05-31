package org.Darwyi.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Random;

public class LogGenerator {

    private static final String[] MODULES = {
        "AuthModule", "DbModule", "NetModule",
        "CoreModule", "UiModule", "CacheModule", "ApiModule"
    };

    private static final String[] MSG_FATAL = {
        "Out of memory, shutting down",
        "Disk full, cannot continue",
        "Unrecoverable error in main loop",
        "Kernel panic in subsystem"
    };
    private static final String[] MSG_ERROR = {
        "Connection timeout to database",
        "Failed to parse JSON payload",
        "Authorization rejected for user",
        "File not found: config.yaml",
        "Null pointer in handler"
    };
    private static final String[] MSG_WARNING = {
        "High memory usage detected (85%)",
        "Slow response from upstream service",
        "Deprecated API endpoint used",
        "Retry attempt 2 of 3"
    };
    private static final String[] MSG_INFO = {
        "User logged in successfully",
        "Cache hit ratio: 0.85",
        "Connection established to upstream",
        "Configuration loaded from disk",
        "Background task started",
        "Request processed in 42 ms"
    };
    private static final String[] MSG_DEBUG = {
        "Entering method handleRequest()",
        "Variable x = 67",
        "Lock acquired on resource pool",
        "Garbage collection triggered",
        "Trace: stack depth = 1488"
    };

    public static void generate(String path, int lines) throws IOException {
        Random rng = new Random(42);
        Instant base = Instant.now().minusSeconds(86_400L);

        try (
            FileWriter     fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter    pw = new PrintWriter(bw)
        ) {
            for (int i = 1; i <= lines; i++) {
                LogLevel level   = pickLevel(rng);
                String   module  = MODULES[rng.nextInt(MODULES.length)];
                String   message = pickMessage(level, rng);
                Instant  ts      = base.plusMillis((long) i * 37 + rng.nextInt(100));

                pw.printf("%08d %s %s %s %s%n",
                    i, ts.toString(), level.getTag(), module, message);
            }
        }
    }

    private static LogLevel pickLevel(Random rng) {
        int r = rng.nextInt(100);
        if (r < 2)  return LogLevel.CRITICAL;
        if (r < 10) return LogLevel.ERROR;
        if (r < 25) return LogLevel.WARNING;
        if (r < 70) return LogLevel.INFO;
        return LogLevel.DEBUG;
    }

    private static String pickMessage(LogLevel level, Random rng) {
        String[] pool;
        switch (level) {
            case CRITICAL: pool = MSG_FATAL; break;
            case ERROR:    pool = MSG_ERROR;    break;
            case WARNING:  pool = MSG_WARNING;  break;
            case INFO:     pool = MSG_INFO;     break;
            default:       pool = MSG_DEBUG;
        }
        return pool[rng.nextInt(pool.length)];
    }
}
