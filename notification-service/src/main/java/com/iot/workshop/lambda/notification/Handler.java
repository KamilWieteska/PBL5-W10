package com.iot.workshop.lambda.notification;

import com.iot.workshop.lambda.notification.data.ThresholdConfigGetRepository;
import com.iot.workshop.lambda.notification.decoder.JsonDecoder;
import com.iot.workshop.lambda.notification.evaluator.ThresholdCrossedEvaluator;
import com.iot.workshop.lambda.notification.integration.EventDecoder;
import com.iot.workshop.lambda.notification.notifications.NotificationFactory;
import com.iot.workshop.lambda.notification.sns.Publisher;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Handler implements RequestHandler<SNSEvent, Void> {

    private static final Logger logger = LoggerFactory.getLogger( Handler.class );

    private static final AmazonSNS snsClient =
            AmazonSNSClient.builder()
                    .withRegion( Regions.EU_NORTH_1 )
                    .build();

    private final EventDecoder<SNSEvent.SNSRecord> eventDecoder;

    private final JsonDecoder decoder;

    private final ThresholdCrossedEvaluator evaluator;

    private final NotificationFactory notificationDispatcher;

    private final Publisher snsPublisher;

    public Handler() {
        eventDecoder = EventDecoder.instance();
        decoder = JsonDecoder.instance();
        evaluator = ThresholdCrossedEvaluator.instance( ThresholdConfigGetRepository.instance() );
        notificationDispatcher = NotificationFactory.instance();
        snsPublisher = Publisher.instance( snsClient );
    }

    @Override
    public Void handleRequest( SNSEvent event, Context context ) {

        logger.info( "Received an event: {}", event );

        event.getRecords()
                .stream()
                .map( eventDecoder::toJson )
                .map( decoder::decode )
                .map( evaluator::evaluate )
                .map( notificationDispatcher::prepareNotification )
                .forEach( snsPublisher::publish );

        return null;
    }
}
