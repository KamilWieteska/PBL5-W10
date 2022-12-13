package com.iot.workshop.lambda.control.model;

public final class DeviceResponse {

    private String deviceId;

    private ThermostatState newState;

    private long timestamp;

    private float targetTemperature;

    /**
     * Drawback of incorporation of runtime based mappers (in this case Jackson), it is enforced to provide a default
     * constructor. The price of it is having mutable object (not desirable from modeling of a system). Gain: very
     * little effort and maintenance is used to decode the payload (from the development point of view).
     */
    public DeviceResponse() {
    }

    public DeviceResponse( String deviceId, ThermostatState newState, long timestamp, float targetTemperature ) {
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

    public ThermostatState getNewState() {
        return newState;
    }

    public void setNewState( ThermostatState newState ) {
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
