package com.iot.workshop.lambda.control.state;

import com.iot.workshop.lambda.control.data.deviceconfig.DeviceConfigRepository;
import com.iot.workshop.lambda.control.data.telemetry.TelemetryGetRepository;
import com.iot.workshop.lambda.control.model.DeviceResponse;
import com.iot.workshop.lambda.control.model.TelemetryEvent;

/**
 * Controls the state of the device-- if the average temperature from last 5 min is above target temperature, set the
 * device to COOLING state. If the temperature is lower-- HEATING. Set IDLE for any other case.
 */
public interface DeviceStateOperator {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static DeviceStateOperator instance( TelemetryGetRepository getRepository, DeviceConfigRepository deviceConfigRepository ) {
        return new DeviceStateOperatorImpl( getRepository, deviceConfigRepository );
    }

    DeviceResponse computeState( TelemetryEvent event );
}
