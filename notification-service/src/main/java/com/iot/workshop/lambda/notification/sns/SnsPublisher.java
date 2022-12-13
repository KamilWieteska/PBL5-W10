package com.iot.workshop.lambda.notification.sns;

import com.iot.workshop.lambda.notification.model.ThresholdCrossedNotification;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SnsPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger( SnsPublisher.class );

    private final AmazonSNS snsClient;

    private final String topicArn;

    public SnsPublisher( AmazonSNS snsClient, String topicArn ) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
    }

    @Override
    public void publish( ThresholdCrossedNotification thresholdCrossedNotification ) {
        if ( thresholdCrossedNotification != null ) {
            pubTopic(
                    thresholdCrossedNotification.getMessage(), thresholdCrossedNotification.getSubject() );
        }
    }

    public void pubTopic( String message, String subject ) {

        logger.info( "Sending the message" );

        PublishRequest request =
                new PublishRequest().withTopicArn( topicArn )
                        .withMessage( message )
                        .withSubject( subject );

        var result = snsClient.publish( request );

        logger.info(
                "{} message sent. Status is {}", result.getMessageId(), result.getSdkResponseMetadata() );
    }
}
