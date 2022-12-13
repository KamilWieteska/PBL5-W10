package com.iot.workshop.lambda.notification.sns;

import com.iot.workshop.lambda.notification.model.ThresholdCrossedNotification;

import com.amazonaws.services.sns.AmazonSNS;

/**
 * Publishes messages to the SNS topic configured through env variable
 */
public interface Publisher {

    static Publisher instance( AmazonSNS client ) {

        return new SnsPublisher( client, System.getenv( "TOPIC_ARN" ) );
    }

    void publish( ThresholdCrossedNotification thresholdCrossedNotification );
}
