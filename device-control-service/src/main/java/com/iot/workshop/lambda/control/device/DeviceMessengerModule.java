package com.iot.workshop.lambda.control.device;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DeviceMessengerModule {

    @Binds
    abstract DeviceMessenger bind(DeviceMessengerImpl deviceMessenger);
}
