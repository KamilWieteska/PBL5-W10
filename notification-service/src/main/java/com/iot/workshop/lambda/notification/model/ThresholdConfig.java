package com.iot.workshop.lambda.notification.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ThresholdConfig")
public final class ThresholdConfig {

    private String deviceId;

    private float targetThreshold;

    public ThresholdConfig() {
    }

    public ThresholdConfig( String deviceId, float targetThreshold ) {
        this.deviceId = deviceId;
        this.targetThreshold = targetThreshold;
    }

    public static ThresholdConfig keyObjectOf( String deviceId ) {
        var keyObject = new ThresholdConfig();
        keyObject.deviceId = deviceId;
        return keyObject;
    }

    @DynamoDBHashKey(attributeName = "deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    @DynamoDBAttribute(attributeName = "targetThreshold")
    public float getTargetThreshold() {
        return targetThreshold;
    }

    public void setTargetThreshold( float targetThreshold ) {
        this.targetThreshold = targetThreshold;
    }
}
