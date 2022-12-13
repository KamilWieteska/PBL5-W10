package com.iot.workshop.devicesimulator.device;

public final class DeviceCertificates {

    private final String deviceCertFilename;

    private final String privateKeyFilename;

    private final String caFileName;

    public DeviceCertificates( String deviceCertFilename, String privateKeyFilename, String caFileName ) {
        this.deviceCertFilename = deviceCertFilename;
        this.privateKeyFilename = privateKeyFilename;
        this.caFileName = caFileName;
    }

    public String getDeviceCertFilename() {
        return deviceCertFilename;
    }

    public String getPrivateKeyFilename() {
        return privateKeyFilename;
    }

    public String getCaFileName() {
        return caFileName;
    }
}
