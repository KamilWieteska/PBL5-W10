package com.iot.workshop.lambda.control.di.component;

import com.iot.workshop.lambda.control.DeviceControlServiceHandler;
import com.iot.workshop.lambda.control.device.DeviceMessengerModule;
import com.iot.workshop.lambda.control.di.module.HandlerModule;
import com.iot.workshop.lambda.control.state.DeviceStateOperatorModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { HandlerModule.class, DeviceMessengerModule.class,
                       DeviceStateOperatorModule.class }, dependencies = { RepositoryComponent.class })
public interface HandlerComponent {

    DeviceControlServiceHandler mainHandler();
}
