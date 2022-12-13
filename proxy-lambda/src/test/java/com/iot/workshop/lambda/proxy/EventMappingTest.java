package com.iot.workshop.lambda.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.proxy.model.TelemetryEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventMappingTest {

    @Test
    @DisplayName("Should map EVENT from JSON to Java object")
    void should_map_event_from_JSON_to_java_OBJECT() throws JsonProcessingException {

        final String expectedDeviceId = "DEVICE-12";

        // WITH
        var jsonPayload = "{\"deviceId\":\"DEVICE-12\",\"timestamp\":1669152494247,\"temperature\":0.7616451}";

        // WHEN
        var object = new ObjectMapper().readValue( jsonPayload, TelemetryEvent.class );

        // THEN
        Assertions.assertNotNull( object );
        Assertions.assertEquals( expectedDeviceId, object.getDeviceId() );

    }
}
