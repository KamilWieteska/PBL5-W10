package com.iot.workshop.lambda.notification.evaluator;

import com.iot.workshop.lambda.notification.data.ThresholdConfigGetRepository;
import com.iot.workshop.lambda.notification.model.TelemetryEvent;
import com.iot.workshop.lambda.notification.model.ThresholdCrossedResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ThresholdCrossedEvaluatorImpl implements ThresholdCrossedEvaluator {

    private static final Logger logger = LoggerFactory.getLogger( ThresholdCrossedEvaluatorImpl.class );

    private final ThresholdConfigGetRepository repository;

    public ThresholdCrossedEvaluatorImpl( ThresholdConfigGetRepository repository ) {
        this.repository = repository;
    }

    private static float computeDifference( float temperature, float threshold ) {
        return Math.abs( temperature - threshold );
    }

    @Override
    public ThresholdCrossedResponse evaluate( TelemetryEvent telemetryEvent ) {

        // FIXME provide your code here
        // get the config from repository
        // if config is null, then throw RuntimeException
        // evaluate the event
        // if threshold is crossed, than return ThresholdCrossedResponse.crossed( ... )

        return ThresholdCrossedResponse.notCrossed();
    }
}
