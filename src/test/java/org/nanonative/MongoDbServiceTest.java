package org.nanonative;

import org.junit.jupiter.api.RepeatedTest;
import org.nanonative.nano.core.Nano;
import org.nanonative.nano.services.logging.model.LogLevel;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.nanonative.nano.services.logging.LogService.CONFIG_LOG_LEVEL;

class MongoDbServiceTest {

    public static final int TEST_REPEAT = 128; // performance and concurrency testing
    public static final LogLevel TEST_LOG_LEVEL = LogLevel.WARN;
    public static final String TEST_MONGO_URI = "mongodb://localhost:27017";
    public static final String TEST_MONGO_DB = "testDB";

    @RepeatedTest(TEST_REPEAT)
    void serviceShouldStart_successfully() {
        final Nano nano = new Nano(
            Map.of(
                CONFIG_LOG_LEVEL, TEST_LOG_LEVEL,
                MongoDbService.CONFIG_MONGO_URI, TEST_MONGO_URI,
                MongoDbService.CONFIG_MONGO_DB, TEST_MONGO_DB
            ),
            new MongoDbService() // Service à tester
        );

        assertThat(nano.isReady()).isTrue();
        assertThat(nano.stop(this.getClass()).waitForStop().isReady()).isFalse();
    }
}
