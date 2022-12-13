package com.iot.workshop.lambda.control.di.component;

import com.iot.workshop.lambda.control.data.telemetry.DataModule;
import com.iot.workshop.lambda.control.data.deviceconfig.DeviceConfigRepository;
import com.iot.workshop.lambda.control.data.telemetry.TelemetryGetRepository;
import com.iot.workshop.lambda.control.di.module.RepositoryModule;
import dagger.Component;

@Component(modules = { RepositoryModule.class, DataModule.class })
public interface RepositoryComponent {

    TelemetryGetRepository telemetryRepository();

    DeviceConfigRepository deviceConfigRepository();
}
