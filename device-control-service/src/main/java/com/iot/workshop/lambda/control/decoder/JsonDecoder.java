package com.iot.workshop.lambda.control.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.control.model.TelemetryEvent;

/**
 * Decodes JSON payload to the {@link com.iot.workshop.lambda.control.model.TelemetryEvent}
 */
public interface JsonDecoder {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static JsonDecoder instance( ObjectMapper objectMapper ) {
        return new JsonDecoderImpl(objectMapper);
    }

    TelemetryEvent decode( String json );
}
