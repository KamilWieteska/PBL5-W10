package com.iot.workshop.lambda.notification.data;

import com.iot.workshop.lambda.notification.model.ThresholdConfig;

/**
 * Repository to access Threshold Config
 */
public interface ThresholdConfigGetRepository {

    static ThresholdConfigGetRepository instance() {
        return new ThresholdConfigGetRepositoryImpl();
    }

    ThresholdConfig get( String deviceId );
}
