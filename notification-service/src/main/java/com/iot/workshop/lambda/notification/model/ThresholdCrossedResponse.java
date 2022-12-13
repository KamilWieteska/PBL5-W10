package com.iot.workshop.lambda.notification.model;

public final class ThresholdCrossedResponse {

    private final boolean isCrossed;

    private final TelemetryEvent sourceEvent;

    private final float difference;

    public ThresholdCrossedResponse( boolean isCrossed, TelemetryEvent sourceEvent, float difference ) {
        this.isCrossed = isCrossed;
        this.sourceEvent = sourceEvent;
        this.difference = difference;
    }

    public static ThresholdCrossedResponse crossed( TelemetryEvent sourceEvent, float difference ) {
        return new ThresholdCrossedResponse( true, sourceEvent, difference );
    }

    public static ThresholdCrossedResponse notCrossed() {
        return new ThresholdCrossedResponse( false, null, 0 );
    }

    public boolean isCrossed() {
        return isCrossed;
    }

    public TelemetryEvent getSourceEvent() {
        return sourceEvent;
    }

    public float getDifference() {
        return difference;
    }
}
