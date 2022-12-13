package com.iot.workshop.lambda.notification.model;

public final class ThresholdCrossedNotification {

    private final String message;

    private final String subject;

    public ThresholdCrossedNotification( String message, String subject ) {
        this.message = message;
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }
}
