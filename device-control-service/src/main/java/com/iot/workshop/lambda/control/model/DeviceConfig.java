package com.iot.workshop.lambda.control.model;

public final class DeviceConfig {

    private final float targetTemperature;

    public DeviceConfig( float targetTemperature ) {
        this.targetTemperature = targetTemperature;
    }

    public float getTargetTemperature() {
        return targetTemperature;
    }
}
