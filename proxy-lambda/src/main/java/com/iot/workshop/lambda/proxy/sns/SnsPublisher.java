package com.iot.workshop.lambda.proxy.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SnsPublisher implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger( SnsPublisher.class );

    private final AmazonSNS snsClient;

    private final String topicArn;

    /**
     * All dependencies are injected during initialization. The class does not have to focus on how to create
     * dependencies.
     *
     * @param snsClient client used to send the message
     * @param topicArn  id of the topic to send to
     */
    public SnsPublisher( AmazonSNS snsClient, String topicArn ) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
    }

    @Override
    public void publish( String telemetryEventJson ) {
        pubTopic( telemetryEventJson );
    }

    public void pubTopic( String message ) {

        logger.debug( "Sending the message" );

        // FIXME: add implementation here

        /*logger.info(
                "{} message sent. Status is {}",
                result.getMessageId(),
                result.getSdkResponseMetadata()
                        .toString() );*/
    }
}
