package com.iot.workshop.lambda.control.state;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DeviceStateOperatorModule {

    @Binds
    public abstract DeviceStateOperator bind(DeviceStateOperatorImpl deviceStateOperator);
}
