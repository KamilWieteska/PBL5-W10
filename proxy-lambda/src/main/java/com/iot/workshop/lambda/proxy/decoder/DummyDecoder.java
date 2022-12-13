package com.iot.workshop.lambda.proxy.decoder;

public class DummyDecoder implements TemperatureDecoder {

    @Override
    public float decode( float rawTemperature ) {
        return 0;
    }
}
