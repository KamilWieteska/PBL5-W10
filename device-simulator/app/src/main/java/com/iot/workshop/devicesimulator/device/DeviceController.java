package com.iot.workshop.devicesimulator.device;

/**
 * Represents the virtual device controller
 */
public interface DeviceController {

    void connect();

    void disconnect();

    void sendTelemetry();

    void send( String json );
}
