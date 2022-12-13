package com.iot.workshop.lambda.proxy;

import com.iot.workshop.lambda.proxy.encoder.JsonEncoder;
import com.iot.workshop.lambda.proxy.model.TelemetryEvent;
import com.iot.workshop.lambda.proxy.sns.Publisher;
import com.iot.workshop.lambda.proxy.decoder.TemperatureDecoder;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class must be public with a default constructor, so AWS (or any other cloud provider) can wrap it upon
 * invocation request
 * <p>
 * Here is example with implementation of {@link RequestHandler} provided by aws sdk, where the 'I' stands for expected
 * input and 'O'-- output
 */
public final class Handler implements RequestHandler<TelemetryEvent, Void> {

    /**
     * Logger defined by the slf4j interface
     */
    private static final Logger logger = LoggerFactory.getLogger( Handler.class );


    /**
     * All dependencies are defined as fields of the class
     */
    private static final AmazonSNS snsClient = AmazonSNSClient.builder()
            .build();

    private final TemperatureDecoder temperatureDecoder;

    private final JsonEncoder jsonEncoder;

    private final Publisher publisher;

    /**
     * Default constructor is provided (it does not take any parameters), so it can be wrapped during the runtime by the
     * lambda container.
     * <p>
     * It all dependencies shall be initialized here
     */
    public Handler() {
        temperatureDecoder = TemperatureDecoder.instance();
        publisher = Publisher.instance( snsClient );
        jsonEncoder = JsonEncoder.instance();
    }

    @Override
    public Void handleRequest( TelemetryEvent event, Context context ) {

        logger.info( "Received telemetry event: {}", event );

        /*
         * Usually the message from device is a raw format (i.e. encoded binary form, or some readings require specific decoding),
         * therefore middleware is required to decode it into unified format, what will be understood by other services
         *
         * Here temperature is being multiplied 10 times
         */
        var decodedTemperature = temperatureDecoder.decode( event.getTemperature() );

        var decodedEvent = new TelemetryEvent( decodedTemperature, event.getTimestamp(), event.getDeviceId() );

        logger.info( "Decoded event: {}", decodedEvent );

        publisher.publish( jsonEncoder.encode( decodedEvent ) );

        /*
         * It is recommended to return codes of the invocation, i.e. 200 OK when no error has been occurred
         */
        return null;
    }
}
