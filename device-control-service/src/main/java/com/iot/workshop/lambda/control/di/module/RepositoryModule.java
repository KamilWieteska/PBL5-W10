package com.iot.workshop.lambda.control.di.module;

import com.iot.workshop.lambda.control.data.deviceconfig.DeviceConfigRepository;
import dagger.Module;
import dagger.Provides;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Module
public class RepositoryModule {

    private static final AmazonDynamoDB DYNAMO_DB =
            AmazonDynamoDBClientBuilder.standard()
                    .withRegion( Regions.EU_NORTH_1 )
                    .build();

    @Provides
    public DeviceConfigRepository deviceConfigRepository() {
        return DeviceConfigRepository.instance();
    }

    @Provides
    public AmazonDynamoDB amazonDynamoDB() {
        return DYNAMO_DB;
    }
}
