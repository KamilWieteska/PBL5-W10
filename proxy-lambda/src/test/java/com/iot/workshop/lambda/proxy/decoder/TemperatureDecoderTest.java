package com.iot.workshop.lambda.proxy.decoder;

import com.iot.workshop.lambda.proxy.model.TelemetryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureDecoderTest {

    private TemperatureDecoder decoderUnderTest;

    /**
     * This method is being executed before each test case (the method with @Test annotation)
     */
    @BeforeEach
    void setUp() {
        decoderUnderTest = TemperatureDecoder.instance();
    }

    @Test
    @DisplayName("Should properly transform expected temperature")
    void handleRequest() {

        final String deviceId = "Device-ABC";
        final long timestamp = System.currentTimeMillis();
        final float rawTemperature = 0.5f;

        final float expectedTemperature = 20.5f;

        // WITH
        var telemetry = new TelemetryEvent( rawTemperature, timestamp, deviceId );

        // WHEN
        var computedTemperature = decoderUnderTest.decode( telemetry.getTemperature() );

        // THEN
        assertEquals( expectedTemperature, computedTemperature );
    }
}