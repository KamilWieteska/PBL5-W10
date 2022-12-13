package com.iot.workshop.lambda.control.data.deviceconfig;

import com.iot.workshop.lambda.control.model.DeviceConfig;

/**
 * Allows fetching settings for a given device
 */
public interface DeviceConfigRepository {

    static DeviceConfigRepository instance() {
        return new DummyDeviceConfigRepository();
    }

    DeviceConfig get(String deviceId);
}
