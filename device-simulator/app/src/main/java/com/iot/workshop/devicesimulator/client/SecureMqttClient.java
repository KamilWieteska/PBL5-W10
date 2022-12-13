package com.iot.workshop.devicesimulator.client;

import software.amazon.awssdk.crt.CRT;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.crt.mqtt.MqttClientConnectionEvents;
import software.amazon.awssdk.crt.mqtt.MqttMessage;
import software.amazon.awssdk.crt.mqtt.QualityOfService;
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

final class SecureMqttClient implements MqttClient {

    private static Logger logger = LoggerFactory.getLogger( SecureMqttClient.class );

    private final String clientId;

    private final String deviceCertFilename;

    private final String privateKeyFilename;

    private final String caFileName;

    private final String endpoint;

    private MqttClientConnection connection;

    public SecureMqttClient(
            String clientId,
            String deviceCertFilename,
            String privateKeyFilename,
            String caFileName,
            String endpoint
                           ) {
        this.clientId = clientId;
        this.deviceCertFilename = deviceCertFilename;
        this.privateKeyFilename = privateKeyFilename;
        this.caFileName = caFileName;
        this.endpoint = endpoint;
    }

    @Override
    public boolean connect() {

        try ( AwsIotMqttConnectionBuilder builder = AwsIotMqttConnectionBuilder
                .newMtlsBuilder(
                        readFileFromResources( deviceCertFilename ),
                        readFileFromResources( privateKeyFilename )
                               )
        ) {
            builder.withCertificateAuthority( readFileFromResources( caFileName ) );

            // TODO: move it to other method / class
            builder.withConnectionEventCallbacks( getCallbacks() )
                    .withClientId( clientId )
                    .withEndpoint( endpoint )
                    .withPort( (short) 8883 )
                    .withCleanSession( true )
                    .withProtocolOperationTimeoutMs( 60000 );

            connection = builder.build();

            if ( connection == null ) {
                logger.error( "Connection creation failed!" );
                throw new RuntimeException( "Could not connect with MQTT broker." );
            }

            CompletableFuture<Boolean> connected = connection.connect();
            boolean sessionPresent = connected.get();

            logger.debug( "Established the connection with the MQTT broker." );

            return true;

        }
        catch ( IOException e ) {
            logger.error( "An error has been occurred while reading cert file: {}", e.getMessage() );
        }
        catch ( ExecutionException e ) {
            logger.error( "An error has been occurred while connecting with the broker: {}", e.getMessage() );
        }
        catch ( InterruptedException e ) {
            logger.error( "Thread interrupted exception: {}", e.getMessage() );
        }

        return false;
    }

    private String readFileFromResources( String filename ) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try ( InputStream is = classLoader.getResourceAsStream( filename ) ) {
            if ( is == null ) {
                throw new IOException( "InputStream empty for file " + filename );
            }
            try ( InputStreamReader isr = new InputStreamReader( is );
                  BufferedReader reader = new BufferedReader( isr ) ) {
                return reader.lines()
                        .collect( Collectors.joining( System.lineSeparator() ) );
            }
        }
    }

    private MqttClientConnectionEvents getCallbacks() {
        return new MqttClientConnectionEvents() {
            @Override
            public void onConnectionInterrupted( int errorCode ) {
                if ( errorCode != 0 ) {
                    logger.error( "Connection interrupted: {}: {}", errorCode, CRT.awsErrorString( errorCode ) );
                }
            }

            @Override
            public void onConnectionResumed( boolean sessionPresent ) {
                logger.info( "Connection resumed: {}", sessionPresent ? "existing session" : "clean session" );
            }
        };
    }

    @Override
    public void sendMessage( String message, String topic ) {

        CompletableFuture<Integer> published = connection.publish( new MqttMessage( topic,
                                                                                    message.getBytes(),
                                                                                    QualityOfService.AT_LEAST_ONCE,
                                                                                    false ) );

        try {
            published.get();
        }
        catch ( InterruptedException | ExecutionException e ) {
            logger.error( "Could not send the message: {}", e.getMessage() );
        }
    }

    @Override
    public void subscribe( String topic ) {

        CompletableFuture<Integer> subscribed = connection.subscribe( topic, QualityOfService.AT_LEAST_ONCE,
                                                                      ( message ) -> {
                                                                          String payload =
                                                                                  new String( message.getPayload(),
                                                                                              StandardCharsets.UTF_8 );
                                                                          logger.info( "MESSAGE: {}", payload );
                                                                      }
                                                                    );

        try {
            subscribed.get();
        }
        catch ( InterruptedException | ExecutionException e ) {
            logger.error( "Could not subscribe onto topic {}: {}", topic, e.getMessage() );
        }
    }

    @Override
    public void subscribe( String topic, Consumer<String> callback ) {

        CompletableFuture<Integer> subscribed = connection.subscribe( topic, QualityOfService.AT_LEAST_ONCE,
                                                                      ( message ) -> {
                                                                          String payload =
                                                                                  new String( message.getPayload(),
                                                                                              StandardCharsets.UTF_8 );
                                                                          logger.debug( "MESSAGE: {}", payload );
                                                                          callback.accept( payload );
                                                                      }
                                                                    );

        try {
            subscribed.get();
        }
        catch ( InterruptedException | ExecutionException e ) {
            logger.error( "Could not subscribe onto topic {}: {}", topic, e.getMessage() );
        }
    }

    @Override
    public void close() {

        CompletableFuture<Void> disconnected = connection.disconnect();
        try {
            disconnected.get();
            logger.debug( "Client disconnected." );
        }
        catch ( InterruptedException e ) {
            logger.error( "Thread interrupted exception: {}", e.getMessage() );
        }
        catch ( ExecutionException e ) {
            logger.error( "Error while disconnecting {}", e.getMessage() );
        }

        connection.close();
    }
}
