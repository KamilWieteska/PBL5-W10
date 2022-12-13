package com.iot.workshop.lambda.control.state;

import com.iot.workshop.lambda.control.data.telemetry.TelemetryGetRepository;
import com.iot.workshop.lambda.control.data.deviceconfig.DeviceConfigRepository;
import com.iot.workshop.lambda.control.model.DeviceResponse;
import com.iot.workshop.lambda.control.model.TelemetryEvent;
import com.iot.workshop.lambda.control.model.ThermostatState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

final class DeviceStateOperatorImpl implements DeviceStateOperator {

    private static final Logger logger = LoggerFactory.getLogger( DeviceStateOperatorImpl.class );

    private static final long FIVE_MINUTES_IN_MILLIS = TimeUnit.MINUTES.toMillis( 5 );

    private final TelemetryGetRepository telemetryGetRepository;

    private final DeviceConfigRepository deviceConfigRepository;

    @Inject
    public DeviceStateOperatorImpl( TelemetryGetRepository telemetryGetRepository,
                                    DeviceConfigRepository deviceConfigRepository ) {
        this.telemetryGetRepository = telemetryGetRepository;
        this.deviceConfigRepository = deviceConfigRepository;
    }

    private static float computeAverageTemp( List<TelemetryEvent> events ) {

        // FIXME: add your code here
        return 0;
    }

    private static ThermostatState evaluateState( float averageTemperature, float targetTemperature ) {

        // FIXME: one state is missing! You must provide an update
        if ( averageTemperature > targetTemperature ) {
            return ThermostatState.COOLING;
        }  else {
            return ThermostatState.IDLE;
        }
    }

    @Override
    public DeviceResponse computeState( TelemetryEvent event ) {

        logger.info(
                "Evaluating state of the device {} based on the telemetry data...", event.getDeviceId() );

        // FIXME: add your code here

        /*logger.info(
                "Average temperature from past 5 min: {}. Setting state to {}");*/

        /*
         * The state shall be stored within DB as well and shall be emitted to the device only when its
         * required to change it
         * For the workshop, we omit that stage
         */
        return null;
    }
}
