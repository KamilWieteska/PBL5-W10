package com.iot.workshop.devicesimulator.client;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

final class MqttClientImpl implements MqttClient {

    private static final int CONNECTION_TIMEOUT = 10;
    private static Logger logger = LoggerFactory.getLogger( MqttClientImpl.class );
    private final String serverUrl;

    private final String clientId;

    private IMqttClient client;

    public MqttClientImpl( String serverUrl, String clientId ) {
        this.serverUrl = serverUrl;
        this.clientId = clientId;
    }

    private static MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect( true );
        options.setCleanSession( true );
        options.setConnectionTimeout( CONNECTION_TIMEOUT );

        return options;
    }

    @Override
    public boolean connect() {
        MqttConnectOptions options = getOptions();

        try {
            client = new org.eclipse.paho.client.mqttv3.MqttClient( serverUrl, clientId );
            client.connect( options );

            return client.isConnected();

        }
        catch ( MqttException e ) {
            logger.error( "An error occurred while connecting to the broker {}, {}", e.getMessage(), e.getCause() );
            return false;
        }
    }

    @Override
    public void sendMessage( String message, String topic ) {
        if ( client != null && client.isConnected() ) {

            MqttMessage mqqtFrame = new MqttMessage( message.getBytes( StandardCharsets.UTF_8 ) );
            mqqtFrame.setQos( 0 );
            mqqtFrame.setRetained( true );

            try {
                client.publish( topic, mqqtFrame );
            }
            catch ( MqttException e ) {
                logger.error( "An error occurred while sending message to the broker {}", e.getMessage() );
            }
        }
    }

    @Override
    public void subscribe( String topic ) {
        try {
            client.subscribe( topic, ( recTopic, message ) -> {
                byte[] payload = message.getPayload();
                logger.info( "Received message[{}]: {}", message.getId(), new String( payload ) );
            } );
        }
        catch ( MqttException e ) {
            logger.error( "An error occurred while subscribing to the broker {}", e.getMessage() );
        }
    }

    @Override
    public void close() {
        if ( client != null ) {
            try {
                client.close();
            }
            catch ( MqttException e ) {
                logger.error( "An error occurred while closing connection to the broker {}", e.getMessage() );
            }
        }
    }
}
