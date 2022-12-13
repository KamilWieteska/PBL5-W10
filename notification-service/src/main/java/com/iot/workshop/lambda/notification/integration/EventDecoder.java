package com.iot.workshop.lambda.notification.integration;

/**
 * Decodes service inbound events. It allows decoupling of the dependencies
 */
public interface EventDecoder<T> {

    static EventDecoder instance() {
        return new EventDecoderImpl();
    }

    /**
     * Unwraps expected event into format understood by the rest of the service or for further processing.
     *
     * @param itemToDecode
     */
    String toJson( T itemToDecode );
}
