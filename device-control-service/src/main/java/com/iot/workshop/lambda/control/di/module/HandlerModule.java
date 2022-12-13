package com.iot.workshop.lambda.control.di.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.workshop.lambda.control.decoder.JsonDecoder;
import com.iot.workshop.lambda.control.encoder.JsonEncoder;
import com.iot.workshop.lambda.control.integration.EventDecoder;
import dagger.Module;
import dagger.Provides;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import javax.inject.Singleton;

@Module
public class HandlerModule {

    private static final AWSIotData IOT_DATA_CLIENT = AWSIotDataClient.builder()
            .withRegion( Regions.EU_NORTH_1 )
            .build();

    @Singleton
    @Provides
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Provides
    public JsonDecoder decoder( ObjectMapper objectMapper ) {
        return JsonDecoder.instance( objectMapper );
    }

    @Provides
    public JsonEncoder encoder( ObjectMapper objectMapper ) {
        return JsonEncoder.instance( objectMapper );
    }

    @Provides
    @SuppressWarnings("unchecked")
    EventDecoder<SNSEvent.SNSRecord> eventDecoder() {
        return EventDecoder.instance();
    }

    @Singleton
    @Provides
    AWSIotData iotClient() {
        return IOT_DATA_CLIENT;
    }


}
