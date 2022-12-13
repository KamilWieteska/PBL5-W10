package com.iot.workshop.lambda.control;

import com.iot.workshop.lambda.control.di.component.DaggerHandlerComponent;
import com.iot.workshop.lambda.control.di.component.DaggerRepositoryComponent;
import com.iot.workshop.lambda.control.di.component.RepositoryComponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

/**
 * This class must be public with a default constructor, so AWS (or any other cloud provider) can wrap it upon
 * invocation request
 * <p>
 * Here is example with implementation of {@link RequestHandler} provided by aws sdk, where the 'I' stands for expected
 * input and 'O'-- output
 */
public final class Handler implements RequestHandler<SNSEvent, Void> {

    private static final RepositoryComponent REPOSITORY_COMPONENT = DaggerRepositoryComponent.create();

    private static final DeviceControlServiceHandler serviceHandler;

    /*
     * It is possible to initialize static dependencies in a 'static block'
     */
    static {
         /*
        Please notice in here that as RepositoryComponent is a dependency to the HandlerComponent,
        it must be provided within the builder in order to inject all expected dependencies
         */
        serviceHandler = DaggerHandlerComponent.builder()
                .repositoryComponent( REPOSITORY_COMPONENT )
                .build()
                .mainHandler();
    }

    @Override
    public Void handleRequest( SNSEvent event, Context context ) {
        return serviceHandler.handle( event );
    }
}
