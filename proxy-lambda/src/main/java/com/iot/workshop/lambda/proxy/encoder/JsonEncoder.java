package com.iot.workshop.lambda.proxy.encoder;

import com.iot.workshop.lambda.proxy.model.TelemetryEvent;

/**
 * Encodes {@link TelemetryEvent} to JSON payload
 */
public interface JsonEncoder {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static JsonEncoder instance() {
        return new JsonEncoderImpl();
    }

    /**
     * @param telemetryEvent to encode into JSON
     *
     * @return JSON payload for the Telemetry event
     */
    String encode( TelemetryEvent telemetryEvent );
}
