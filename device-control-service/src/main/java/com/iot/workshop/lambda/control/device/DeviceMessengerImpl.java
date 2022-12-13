package com.iot.workshop.lambda.control.device;

import com.iot.workshop.lambda.control.encoder.JsonEncoder;
import com.iot.workshop.lambda.control.model.DeviceResponse;

import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.model.PublishRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

final class DeviceMessengerImpl implements DeviceMessenger {

    private static final Logger logger = LoggerFactory.getLogger( DeviceMessengerImpl.class );

    private static final int QOS = 1;

    private static final String DEVICE_TOPIC_TEMPLATE = "commands/%s";

    private final AWSIotData client;

    private final JsonEncoder encoder;

    @Inject
    public DeviceMessengerImpl( JsonEncoder encoder, AWSIotData client ) {
        this.encoder = encoder;
        this.client = client;
    }

    private static PublishRequest frame( String json, String deviceId ) {

        return new PublishRequest()
                .withQos( QOS )
                .withTopic( String.format( DEVICE_TOPIC_TEMPLATE, deviceId ) )
                .withPayload( ByteBuffer.wrap( json.getBytes( StandardCharsets.UTF_8 ) ) );
    }

    @Override
    public void sendMessage( DeviceResponse deviceResponse ) {

        logger.debug( "Preparing the message for the device {}", deviceResponse.getDeviceId() );

        var encodedPayload = encoder.encode( deviceResponse );

        logger.info(
                "Sending message with payload: {} for device {}",
                encodedPayload,
                deviceResponse.getDeviceId() );
        var result = client.publish( frame( encodedPayload, deviceResponse.getDeviceId() ) );

        logger.debug( "Message published, the response code: {}", result );
    }
}
