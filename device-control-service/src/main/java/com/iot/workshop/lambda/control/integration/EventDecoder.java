package com.iot.workshop.lambda.control.integration;

/**
 * Decodes service inbound events
 */
public interface EventDecoder<T> {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static EventDecoder instance() {
        return new EventDecoderImpl();
    }

    String toJson( T itemToDecode );
}
