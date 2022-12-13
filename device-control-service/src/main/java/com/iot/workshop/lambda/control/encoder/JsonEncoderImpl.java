package com.iot.workshop.lambda.control.encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.control.model.DeviceResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

final class JsonEncoderImpl implements JsonEncoder {

    private static final Logger logger = LoggerFactory.getLogger( JsonEncoderImpl.class );

    private final ObjectMapper objectMapper;

    @Inject
    public JsonEncoderImpl( ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String encode( DeviceResponse itemToEncode ) {

        try {
            return objectMapper.writeValueAsString( itemToEncode );

        }
        catch ( JsonProcessingException e ) {
            logger.error( "Error while encoding: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
