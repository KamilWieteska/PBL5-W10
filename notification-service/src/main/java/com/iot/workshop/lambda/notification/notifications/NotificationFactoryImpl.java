package com.iot.workshop.lambda.notification.notifications;

import com.iot.workshop.lambda.notification.model.ThresholdCrossedNotification;
import com.iot.workshop.lambda.notification.model.ThresholdCrossedResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class NotificationFactoryImpl implements NotificationFactory {

    private static final Logger logger = LoggerFactory.getLogger( NotificationFactoryImpl.class );

    /**
     * Please keep in mind that this value is hardcoded in here for sake of workshop simplification, but in a real world
     * conditions, it shall be fetched from the DB
     */
    private static final float TARGET_TEMPERATURE = 18.f;

    private static final String MESSAGE_TEMPLATE =
            "The device %s has crossed defined temperature setting for %.2f\u00B0 by %.2f\u00B0 C!";

    private static final String NOTIFICATION_SUBJECT_TEMPLATE = "High temperature detected for %s";

    @Override
    public ThresholdCrossedNotification prepareNotification( ThresholdCrossedResponse response ) {

        if ( !response.isCrossed() ) {
            logger.debug( "Reading is fine, omitting notification" );
            /*
             * Please note that in commercial conditions you should never return a null from publicly accessed method!
             * Instead, add Optional type to communicate that the response may be empty
             */
            return null;
        }

        // FIXME: provide your code here

        return null/*new ThresholdCrossedNotification( message, subject )*/;
    }
}
