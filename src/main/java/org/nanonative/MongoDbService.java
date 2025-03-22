package org.nanonative;

import berlin.yuna.typemap.model.LinkedTypeMap;
import berlin.yuna.typemap.model.TypeMapI;
import org.nanonative.nano.core.model.Service;
import org.nanonative.nano.helper.event.model.Event;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.nanonative.nano.services.logging.LogService;

import static org.nanonative.nano.helper.config.ConfigRegister.registerConfig;


/**
 * SEE ALSO {@link org.nanonative.nano.services.logging.LogService} for example implementation
 */
public class MongoDbService extends Service {

    public static final String CONFIG_MONGO_URI = registerConfig(
        "mongo.uri",
        "MongoDB connection URI (e.g. mongodb://localhost:27017)"
    );

    public static final String CONFIG_MONGO_DB = registerConfig(
        "mongo.db",
        "Database name"
    );

    private MongoClient mongoClient;
    private MongoDatabase database;
    private String databaseName;
    private String uri;
    private LogService logService;

    /**
     * Starts the service. This method is called during service initialization.
     * Optional implementation - services can leave this empty if no startup logic is needed.
     * Common uses include:
     * - Initializing resources
     * - Setting up connections
     * - Starting background tasks
     * See also {@link Service#configure(TypeMapI, TypeMapI)} for configuration setup.
     */
    @Override
    public void start() {
        this.logService = context.service(LogService.class);
        this.uri = context.asString(CONFIG_MONGO_URI);
        this.databaseName = context.asString(CONFIG_MONGO_DB);

        log(Level.INFO, "Starting MongoDB service...");

        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(databaseName);

            log(Level.INFO, "Connected to MongoDB [URI: " + uri + "]");

        } catch (Exception e) {
            log(Level.SEVERE, "Connection failed: " + e.getMessage(), e);
        }
    }

    /**
     * Stops the service gracefully. This method is called during service shutdown.
     * Optional implementation - services can leave this empty if no cleanup is needed.
     * Common uses include:
     * - Closing connections
     * - Releasing resources
     * - Stopping background tasks
     */
    @Override
    public void stop() {

    }

    /**
     * Handles service failures and errors.
     * Optional implementation - services can return null if no specific error handling is needed.
     * Null means the error will be logged automatically if no other listener or service handles it.
     * Useful for:
     * - Custom error recovery strategies
     * - Error logging
     * - Notifying other components of failures
     *
     * @param error The error event to handle
     * @return Response object from error handling, can be null
     */
    @Override
    public Object onFailure(final Event error) {
        return null;
    }

    /**
     * Processes incoming events for the service.
     * Optional implementation - services can leave this empty if they don't need to handle events.
     * Useful for:
     * - Responding to system events
     * - Inter-service communication
     * - State updates based on external triggers
     *
     * @param event The event to process
     */
    @Override
    public void onEvent(final Event event) {

    }

    /**
     * Configures the service with changes while maintaining merged state.
     * Optional implementation - services can leave this empty if no configuration is needed.
     * Useful for:
     * - Service initialization with configuration
     * - Handling dynamic configuration updates
     * - Managing service state
     * - Applying configuration changes without service restart
     *
     * @param changes The new configuration changes to apply
     * @param merged  The complete merged configuration state - this will be represented in {@link Service#context} after the method is done
     */
    @Override
    public void configure(final TypeMapI<?> changes, final TypeMapI<?> merged) {

    }

    @Override
    public String toString() {
        return new LinkedTypeMap()
            .putR("name", this.getClass().getSimpleName())
            .toJson();
    }

    /**
     * Logs a message with the specified log level.
     *
     * @param level   The severity level of the message (e.g., INFO, WARNING, SEVERE)
     * @param message The descriptive message to log
     */
    private void log(Level level, String message) {
        log(level, message, null);
    }

    /**
     * Logs a message with the specified log level and associated throwable.
     *
     * @param level     The severity level of the message (e.g., INFO, WARNING, SEVERE)
     * @param message   The descriptive message to log
     * @param throwable The exception/error associated with the log entry (nullable)
     */
    private void log(Level level, String message, Throwable throwable) {
        if (logService != null) {
            final LogRecord logRecord = new LogRecord(level, message);
            logRecord.setLoggerName("MongoDbService");
            if (throwable != null) {
                logRecord.setThrown(throwable);
            }
            logService.log(() -> logRecord);
        }
    }
}
