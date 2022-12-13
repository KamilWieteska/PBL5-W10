package com.iot.workshop.lambda.notification.decoder;

import com.iot.workshop.lambda.notification.model.TelemetryEvent;

/**
 * Decodes JSON payload to the {@link TelemetryEvent}
 */
public interface JsonDecoder {

    static JsonDecoder instance() {
        return new JsonDecoderImpl();
    }

    TelemetryEvent decode( String json );
}
