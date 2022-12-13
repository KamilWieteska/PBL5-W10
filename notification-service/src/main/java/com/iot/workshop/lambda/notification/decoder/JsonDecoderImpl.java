package com.iot.workshop.lambda.notification.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.notification.model.TelemetryEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class JsonDecoderImpl implements JsonDecoder {

    private static final Logger logger = LoggerFactory.getLogger( JsonDecoderImpl.class );

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public TelemetryEvent decode( String json ) {
        try {
            return OBJECT_MAPPER.readValue( json, TelemetryEvent.class );

        }
        catch ( JsonProcessingException e ) {
            logger.error( "Error while decoding: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
