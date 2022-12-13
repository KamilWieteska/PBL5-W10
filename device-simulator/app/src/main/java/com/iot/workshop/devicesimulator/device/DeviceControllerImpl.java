package com.iot.workshop.devicesimulator.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.devicesimulator.client.MqttClient;
import com.iot.workshop.devicesimulator.model.CloudCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

class DeviceControllerImpl implements DeviceController {

    private static Logger logger = LoggerFactory.getLogger( DeviceControllerImpl.class );

    private static final String TELEMETRY_TOPIC_TEMPLATE = "telemetry/%s";
    private static final String COMMAND_TOPIC_TEMPLATE = "commands/%s";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String telemetryTopic;

    private final String commandTopic;

    private final String endpoint;

    private final long periodInSeconds;

    private final String deviceId;

    private MqttClient mqttClient;

    private String state = "IDLE";

    private float targetTemperature = 15.f;

    DeviceControllerImpl(
            String endpoint, long periodInSeconds, CertificateProvider provider, String deviceId ) {
        this.endpoint = endpoint;
        this.periodInSeconds = periodInSeconds;

        this.deviceId = deviceId;

        var certs = provider.get( deviceId );

        // TODO: move to the class or even external factory
        telemetryTopic = String.format( TELEMETRY_TOPIC_TEMPLATE, deviceId );
        commandTopic = String.format( COMMAND_TOPIC_TEMPLATE, deviceId );

        // TODO: move this as well
        mqttClient =
                MqttClient.of(
                        endpoint,
                        deviceId,
                        certs.getDeviceCertFilename(),
                        certs.getPrivateKeyFilename(),
                        certs.getCaFileName() );
    }

    private static float getCurrentTemperature() {
        return ThreadLocalRandom.current()
                .nextFloat();
    }

    @Override
    public void connect() {
        mqttClient.connect();
        mqttClient.subscribe( telemetryTopic );

        mqttClient.subscribe(
                commandTopic,
                ( message ) -> {
                    try {
                        var command = OBJECT_MAPPER.readValue( message, CloudCommand.class );

                        logger.info( "Received command from the cloud: {}", message );

                        if ( !command.getNewState()
                                .equals( state ) ) {
                            logger.info( "Changing state from {} to {}", state, command.getNewState() );
                            state = command.getNewState();
                        }

                        if ( targetTemperature != command.getTargetTemperature() ) {
                            logger.info( "Setting target temperature to {} C", command.getTargetTemperature() );
                            targetTemperature = command.getTargetTemperature();
                        }

                    }
                    catch ( JsonProcessingException e ) {
                        logger.error( "Error while decoding message {}", e.getMessage() );
                    }
                } );
    }

    @Override
    public void disconnect() {
        mqttClient.close();
    }

    @Override
    public void sendTelemetry() {
        try {
            mqttClient.sendMessage(
                    OBJECT_MAPPER.writeValueAsString( new DeviceTelemetryPayload( getCurrentTemperature() ) ),
                    telemetryTopic );
        }
        catch ( JsonProcessingException e ) {
            logger.error( "Could not encode the temperature payload: {}", e.getMessage() );
        }
    }

    @Override
    public void send( String json ) {
        mqttClient.sendMessage( json, telemetryTopic );
    }

    private class DeviceTelemetryPayload {

        private final String deviceId = DeviceControllerImpl.this.deviceId;

        private final long arrivalTimestamp = System.currentTimeMillis();

        private float temperature;

        public DeviceTelemetryPayload( float temperature ) {
            this.temperature = temperature;
        }

        public float getTemperature() {
            return temperature;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public long getTimestamp() {
            return arrivalTimestamp;
        }
    }
}
