package com.iot.workshop.lambda.control.data.deviceconfig;

import com.iot.workshop.lambda.control.model.DeviceConfig;

/**
 * To simplify this workshop, no real config management is done here, thus dummy config is being returned
 */
class DummyDeviceConfigRepository implements DeviceConfigRepository {

    /**
     * Please note this shall be configurable value fetched from the DB-- for need of this workshop it is hard-coded
     */
    private static final float TARGET_TEMPERATURE = 18.f;

    @Override
    public DeviceConfig get( String deviceId ) {
        return new DeviceConfig( TARGET_TEMPERATURE );
    }
}
