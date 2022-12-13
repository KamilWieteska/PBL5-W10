package com.iot.workshop.lambda.control.data.telemetry;

import com.iot.workshop.lambda.control.model.TelemetryEvent;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

final class TelemetryGetRepositoryImpl implements TelemetryGetRepository {

    private static final Logger logger = LoggerFactory.getLogger( TelemetryGetRepositoryImpl.class );

    private final AmazonDynamoDB client;

    /**
     * This provides basic functionality of mapping DB entities into Java objects (aka POJOs-- Plain Old Java Objects)
     * and vice versa. Thanks to that it is not needed to write queries / requests and mapping manually. Still, for an
     * object to be mapped, proper annotations must be defined. Sadly, they do not fall into the standardized JPA.
     */
    private final DynamoDBMapper mapper;

    @Inject
    public TelemetryGetRepositoryImpl( AmazonDynamoDB client ) {
        this.client = client;
        this.mapper = new DynamoDBMapper( client );
    }

    @Override
    public List<TelemetryEvent> getEventsFromRange( String deviceId, long timestampBoundInclusive ) {

        // FIXME: add your code here
        return Collections.emptyList();
    }
}
