package com.iot.workshop.lambda.notification.notifications;

import com.iot.workshop.lambda.notification.model.ThresholdCrossedNotification;
import com.iot.workshop.lambda.notification.model.ThresholdCrossedResponse;

/**
 * Produces the {@link ThresholdCrossedNotification} what will be sent to the end-user
 */
public interface NotificationFactory {

    static NotificationFactory instance() {

        return new NotificationFactoryImpl();
    }

    ThresholdCrossedNotification prepareNotification( ThresholdCrossedResponse response );
}
