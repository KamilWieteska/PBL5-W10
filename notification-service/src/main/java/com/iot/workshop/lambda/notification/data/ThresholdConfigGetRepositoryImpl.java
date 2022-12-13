package com.iot.workshop.lambda.notification.data;

import com.iot.workshop.lambda.notification.model.ThresholdConfig;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ThresholdConfigGetRepositoryImpl implements ThresholdConfigGetRepository {

    private static final Logger logger =
            LoggerFactory.getLogger( ThresholdConfigGetRepositoryImpl.class );

    private final AmazonDynamoDB client =
            AmazonDynamoDBClientBuilder.standard()
                    .withRegion( Regions.EU_NORTH_1 )
                    .build();

    private final DynamoDBMapper mapper = new DynamoDBMapper( client );

    @Override
    public ThresholdConfig get( String deviceId ) {

        return mapper.load( ThresholdConfig.keyObjectOf( deviceId ) );
    }
}
