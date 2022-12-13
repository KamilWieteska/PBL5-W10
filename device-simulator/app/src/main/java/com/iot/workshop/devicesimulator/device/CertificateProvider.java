package com.iot.workshop.devicesimulator.device;

public final class CertificateProvider {

    public static CertificateProvider instance() {
        return new CertificateProvider();
    }

    // This is mocked method, in real it shall return certificates accordingly to the deviceId
    public DeviceCertificates get( String deviceId ) {
        return new DeviceCertificates(
                "device.pem.crt",
                "private.pem.key",
                "AmazonRootCA1.pem"
        );
    }
}