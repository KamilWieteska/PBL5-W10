package com.iot.workshop.lambda.control.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TelemetryData")
public final class TelemetryEvent {

    private float temperature;

    private long timestamp;

    private String deviceId;

    /**
     * Drawback of incorporation of runtime based mappers (in this case Jackson), it is enforced to provide a default
     * constructor. The price of it is having mutable object (not desirable from modeling of a system). Gain: very
     * little effort and maintenance is used to decode the payload (from the development point of view).
     */
    public TelemetryEvent() {
    }

    public TelemetryEvent( float temperature, long timestamp, String deviceId ) {
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
    }

    @DynamoDBAttribute(attributeName = "temperature")
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature( float temperature ) {
        this.temperature = temperature;
    }

    @DynamoDBRangeKey(attributeName = "arrivalTimestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( long timestamp ) {
        this.timestamp = timestamp;
    }

    @DynamoDBHashKey(attributeName = "deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return String.format(
                "DeviceId: %s, timestamp: %s, temperature: %s", deviceId, timestamp, temperature );
    }
}
