package com.iot.workshop.lambda.notification.model;

public final class TelemetryEvent {

    private float temperature;

    private long timestamp;

    private String deviceId;

    public TelemetryEvent() {
    }

    public TelemetryEvent( float temperature, long timestamp, String deviceId ) {
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature( float temperature ) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( long timestamp ) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return String.format(
                "DeviceId: %s, timestamp: %s, temperature: %s", deviceId, timestamp, temperature );
    }
}
