package com.iot.workshop.lambda.control.data.telemetry;

import com.iot.workshop.lambda.control.model.TelemetryEvent;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.util.List;

/**
 * Repository to access {@link TelemetryEvent} within configured DB
 * <p>
 * Please note that it does not define underlying DB platform (how to store data). It is separation through abstraction,
 * making this layer independent of implementation. Thanks to that it is possible to switch into a different DB platform
 * (i.e. to decrease the cost).
 */
public interface TelemetryGetRepository {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static TelemetryGetRepository instance( AmazonDynamoDB client ) {
        return new TelemetryGetRepositoryImpl(client);

    }

    List<TelemetryEvent> getEventsFromRange( String deviceId, long timestampBoundInclusive );
}
