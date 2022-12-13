package com.iot.workshop.lambda.control.device;

import com.iot.workshop.lambda.control.encoder.JsonEncoder;
import com.iot.workshop.lambda.control.model.DeviceResponse;

import com.amazonaws.services.iotdata.AWSIotData;

/**
 * Sends the message to the device through given topic
 */
public interface DeviceMessenger {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static DeviceMessenger instance( JsonEncoder encoder, AWSIotData client ) {
        return new DeviceMessengerImpl( encoder, client );
    }

    void sendMessage( DeviceResponse deviceResponse );
}
