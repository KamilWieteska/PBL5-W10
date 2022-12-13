package com.iot.workshop.lambda.proxy.encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.proxy.model.TelemetryEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class JsonEncoderImpl implements JsonEncoder {

    private static final Logger logger = LoggerFactory.getLogger( JsonEncoderImpl.class );

    private final ObjectMapper objectMapper;

    public JsonEncoderImpl() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public String encode( TelemetryEvent telemetryEvent ) {

        try {
            return objectMapper.writeValueAsString( telemetryEvent );
        }
        catch ( JsonProcessingException e ) {
            logger.error( "Error while encoding {}", e.getMessage() );
        }
        /*
         * Returning NULL is never a good idea, unless You have a real reason to. For simplicity, it can be forgiven.
         * In a real world, wrap the exception into custom exception or return {@link java.util.Optional}.
         */
        return null;
    }
}
