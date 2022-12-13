package com.iot.workshop.devicesimulator.model;

public class CloudCommand {

    private String deviceId;

    private String newState;

    private long timestamp;

    private float targetTemperature;

    public CloudCommand() {
    }

    public CloudCommand( String deviceId, String newState, long timestamp, float targetTemperature ) {
        this.deviceId = deviceId;
        this.newState = newState;
        this.timestamp = timestamp;
        this.targetTemperature = targetTemperature;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState( String newState ) {
        this.newState = newState;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( long timestamp ) {
        this.timestamp = timestamp;
    }

    public float getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature( float targetTemperature ) {
        this.targetTemperature = targetTemperature;
    }
}
