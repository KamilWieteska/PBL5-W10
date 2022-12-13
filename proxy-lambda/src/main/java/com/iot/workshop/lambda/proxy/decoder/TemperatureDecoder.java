package com.iot.workshop.lambda.proxy.decoder;

/**
 * Transforms received temperature from a raw format to human(system)-readable
 */
public interface TemperatureDecoder {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static TemperatureDecoder instance() {
        return new DummyDecoder();
    }

    float decode( float rawTemperature );
}
