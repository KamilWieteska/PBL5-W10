package com.iot.workshop.lambda.control;

import com.iot.workshop.lambda.control.decoder.JsonDecoder;
import com.iot.workshop.lambda.control.device.DeviceMessenger;
import com.iot.workshop.lambda.control.integration.EventDecoder;
import com.iot.workshop.lambda.control.state.DeviceStateOperator;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Handler of the service behind the public facade-- Handler-- being exposed to the cloud environment
 */
public final class DeviceControlServiceHandler {

    /**
     * Logger defined by the slf4j interface
     */
    private static final Logger logger = LoggerFactory.getLogger( DeviceControlServiceHandler.class );

    /**
     * All dependencies are defined as fields of the class
     * <p>
     * {@link EventDecoder} provides additional separation of concerns
     */
    private final EventDecoder<SNSEvent.SNSRecord> eventDecoder;

    private final JsonDecoder decoder;

    private final DeviceStateOperator deviceStateOperator;

    private final DeviceMessenger deviceMessenger;

    /**
     * All dependencies are being injected by DI implementation
     */
    @Inject
    public DeviceControlServiceHandler( EventDecoder<SNSEvent.SNSRecord> eventDecoder,
                                        JsonDecoder decoder,
                                        DeviceStateOperator deviceStateOperator,
                                        DeviceMessenger deviceMessenger ) {
        this.eventDecoder = eventDecoder;
        this.decoder = decoder;
        this.deviceStateOperator = deviceStateOperator;
        this.deviceMessenger = deviceMessenger;
    }

    public Void handle( SNSEvent event ) {

        logger.info( "Received an event: {}", event );

        event.getRecords()
                .stream()
                .map( eventDecoder::toJson )
                .map( decoder::decode )
                .map( deviceStateOperator::computeState )
                .forEach( deviceMessenger::sendMessage );

        return null;
    }
}
