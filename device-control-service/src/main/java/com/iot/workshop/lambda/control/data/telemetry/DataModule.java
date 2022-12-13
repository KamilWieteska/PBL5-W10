package com.iot.workshop.lambda.control.data.telemetry;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DataModule {

    @Binds
    public abstract TelemetryGetRepository telemetryGetRepository( TelemetryGetRepositoryImpl repository );
}
