package com.iot.workshop.lambda.proxy.sns;

import com.amazonaws.services.sns.AmazonSNS;

public interface Publisher {

    /**
     * It is called a "factory method". It allows separation of this package from the rest of the system, enforcing it
     * to relay on the abstraction (interface) rather than the underlying concrete implementation. Along with such
     * practice Implementation classes have defined default access modifier (package private).
     */
    static Publisher instance( AmazonSNS client ) {

        /**
         * topic ARN what is needed to identify properly where to send the message is fetched during runtime from
         * as environment variable.
         */
        return new SnsPublisher( client, System.getenv( "TOPIC_ARN" ) );
    }

    void publish( String telemetryEventJson );
}
