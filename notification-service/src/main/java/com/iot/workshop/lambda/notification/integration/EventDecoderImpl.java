package com.iot.workshop.lambda.notification.integration;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class EventDecoderImpl implements EventDecoder<SNSEvent.SNSRecord> {

    private static final Logger logger = LoggerFactory.getLogger( EventDecoderImpl.class );

    @Override
    public String toJson( SNSEvent.SNSRecord itemToDecode ) {

        var sns = itemToDecode.getSNS();

        logger.debug( "Received message with: {} and content: {}", sns.getMessageId(), sns.getMessage() );

        return sns.getMessage();
    }
}
