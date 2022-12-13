package com.iot.workshop.devicesimulator.client;

import java.util.function.Consumer;

public interface MqttClient {

    static MqttClient of(
            String endpoint,
            String clientId,
            String certFileName,
            String privateKeyFileName,
            String caFileName
                        ) {
        return new SecureMqttClient( clientId, certFileName, privateKeyFileName, caFileName, endpoint );
    }

    boolean connect();

    void sendMessage( String message, String topic );

    void subscribe( String topic );

    default void subscribe( String topic, Consumer<String> callback ) {
        // added default in sake of compatibility with other implementations
    }

    void close();
}
