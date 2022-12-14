package com.iot.workshop.lambda.control.state;

import com.iot.workshop.lambda.control.data.deviceconfig.DeviceConfigRepository;
import com.iot.workshop.lambda.control.data.telemetry.TelemetryGetRepository;
import com.iot.workshop.lambda.control.model.TelemetryEvent;
import com.iot.workshop.lambda.control.model.ThermostatState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.lang.Math;

import static org.mockito.Mockito.when;

enum GenerationScenario {

    RISING,
    DROPPING,
    STALE
}

class DeviceStateOperatorTest {

    private static final long FIVE_MINUTES_IN_MILLIS = TimeUnit.MINUTES.toMillis( 5 );

    private static final float TARGET_TEMPERATURE = 18.f;

    private static final String DEVICE_ID = "TEST-ID";

    private DeviceStateOperator stateOperatorUnderTest;

    private long latestTimestamp = System.currentTimeMillis();

    private TelemetryEvent masterEvent;

    @Mock
    private TelemetryGetRepository repository;

    @BeforeEach
    void setUp() {

        /*
         * Allows using Mock annotations with JUnit 5
         */
        MockitoAnnotations.openMocks( this );

        masterEvent = TestDataGenerator.getEvent( latestTimestamp, TARGET_TEMPERATURE );

        /*
        Injecting mocked repository into the class under the test
         */
        stateOperatorUnderTest = DeviceStateOperator.instance( repository, DeviceConfigRepository.instance() );
    }

    @Test
    void should_return_HEATING_when_temperature_dropped() {

        // WITH
        when( repository.getEventsFromRange( DEVICE_ID, latestTimestamp - FIVE_MINUTES_IN_MILLIS ) )
                .thenReturn( TestDataGenerator.getDBResponse( GenerationScenario.DROPPING,
                                                              masterEvent.getTimestamp(),
                                                              FIVE_MINUTES_IN_MILLIS,
                                                              TARGET_TEMPERATURE ) );

        // WHEN
        var computation = stateOperatorUnderTest.computeState( masterEvent );

        // THEN
        Assertions.assertEquals( ThermostatState.HEATING, computation.getNewState() );
    }

    @Test
    void should_return_COOLING_when_temperature_risen() {

        // WITH
        when( repository.getEventsFromRange( DEVICE_ID, latestTimestamp - FIVE_MINUTES_IN_MILLIS ) )
                .thenReturn( TestDataGenerator.getDBResponse( GenerationScenario.RISING,
                                                              masterEvent.getTimestamp(),
                                                              FIVE_MINUTES_IN_MILLIS,
                                                              TARGET_TEMPERATURE ) );

        // WHEN
        var computation = stateOperatorUnderTest.computeState( masterEvent );

        // THEN
        Assertions.assertEquals( ThermostatState.COOLING, computation.getNewState() );
    }

    @Test
    void should_return_IDLE_when_temperature_stale() {

        // WITH
        when( repository.getEventsFromRange( DEVICE_ID, latestTimestamp - FIVE_MINUTES_IN_MILLIS ) )
                .thenReturn( TestDataGenerator.getDBResponse( GenerationScenario.STALE,
                                                              masterEvent.getTimestamp(),
                                                              FIVE_MINUTES_IN_MILLIS,
                                                              TARGET_TEMPERATURE ) );

        // WHEN
        var computation = stateOperatorUnderTest.computeState( masterEvent );

        // THEN
        Assertions.assertEquals( ThermostatState.IDLE, computation.getNewState() );
    }
}

class TestDataGenerator {

    static TelemetryEvent getEvent( long timestamp, float temperature ) {
        final String deviceId = "TEST-ID";

        return new TelemetryEvent( temperature, timestamp, deviceId );
    }

    static List<TelemetryEvent> getDBResponse( GenerationScenario scenario, long start, long duration, float baseTemperature ) {

        final int desiredEvents = 10;
        final long interval = duration / desiredEvents;

        switch ( scenario ) {
            case STALE:
                return generate( start, start - duration, interval, baseTemperature );
            case RISING:
                return generate( start, start - duration, interval, baseTemperature, baseTemperature + 10 );
            case DROPPING:
                return generate( start, start - duration, interval, baseTemperature - 10, baseTemperature );
            default:
                throw new RuntimeException( "Scenario unknown" );
        }
    }

    private static List<TelemetryEvent> generate( long start, long end, long interval, float lowerBound, float upperBound ) {
        return LongStream.iterate( start, value -> value + interval )
                .limit( ( start - end ) / interval )
                .boxed()
                .map( timestamp -> getEvent( timestamp, getRandomFloat( lowerBound, upperBound ) ) )
                .collect( Collectors.toList() );

    }

    private static List<TelemetryEvent> generate( long start, long end, long interval, float fixedTemperature ) {
        return LongStream.iterate( start, value -> value + interval )
                .limit( ( start - end ) / interval )
                .boxed()
                .map( timestamp -> getEvent( timestamp, fixedTemperature ) )
                .collect( Collectors.toList() );

    }

    private static float getRandomFloat( float lowerBound, float upperBound ) {
        return lowerBound + (float)Math.random() * (upperBound - lowerBound);
    }

}
