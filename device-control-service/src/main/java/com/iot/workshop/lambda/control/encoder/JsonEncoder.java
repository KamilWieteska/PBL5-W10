package com.iot.workshop.lambda.control.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.control.model.DeviceResponse;

/**
 * Encodes {@link com.iot.workshop.lambda.control.model.DeviceResponse} to JSON format
 */
public interface JsonEncoder {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static JsonEncoder instance( ObjectMapper objectMapper ) {
        return new JsonEncoderImpl( objectMapper );
    }

    String encode( DeviceResponse itemToEncode );
}
