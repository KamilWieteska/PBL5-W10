package com.iot.workshop.lambda.notification.evaluator;

import com.iot.workshop.lambda.notification.data.ThresholdConfigGetRepository;
import com.iot.workshop.lambda.notification.model.TelemetryEvent;
import com.iot.workshop.lambda.notification.model.ThresholdCrossedResponse;

/**
 * Evaluates if the defined Threshold for the {@link com.iot.workshop.lambda.notification.model.TelemetryEvent} has been
 * crossed
 */
public interface ThresholdCrossedEvaluator {

    static ThresholdCrossedEvaluator instance( ThresholdConfigGetRepository repository ) {
        return new ThresholdCrossedEvaluatorImpl( repository );
    }

    ThresholdCrossedResponse evaluate( TelemetryEvent telemetryEvent );
}
