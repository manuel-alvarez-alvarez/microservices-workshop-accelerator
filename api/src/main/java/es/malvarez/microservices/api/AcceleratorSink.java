package es.malvarez.microservices.api;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Stream for the events coming from the accelerator
 */
public interface AcceleratorSink {

    String INPUT = "accelerator_input";

    @Input(AcceleratorSink.INPUT)
    SubscribableChannel input();
}
