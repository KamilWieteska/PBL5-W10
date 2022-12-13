package com.iot.workshop.devicesimulator.device;

public final class DeviceFactory {

    private static final long PERIOD = 10L;

    public static DeviceController device( String endpoint, CertificateProvider provider, String deviceId ) {
        return new DeviceControllerImpl( endpoint, PERIOD, provider, deviceId );
    }
}
