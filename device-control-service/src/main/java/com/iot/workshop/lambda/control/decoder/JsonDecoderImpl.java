package com.iot.workshop.lambda.control.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.control.model.TelemetryEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

final class JsonDecoderImpl implements JsonDecoder {

    private static final Logger logger = LoggerFactory.getLogger( JsonDecoderImpl.class );

    private final ObjectMapper objectMapper;

    @Inject
    public JsonDecoderImpl( ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public TelemetryEvent decode( String json ) {
        try {
            return objectMapper.readValue( json, TelemetryEvent.class );

        }
        catch ( JsonProcessingException e ) {
            logger.error( "Error while decoding: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
