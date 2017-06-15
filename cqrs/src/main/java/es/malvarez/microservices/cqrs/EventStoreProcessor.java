package es.malvarez.microservices.cqrs;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * This is the place where we throw all the garba... events! and where all the events from the event store come to die
 */
public interface EventStoreProcessor {

    String INPUT = "event_store_input";
    String OUTPUT = "event_store_output";

    @Input(EventStoreProcessor.INPUT)
    SubscribableChannel input();

    @Output(EventStoreProcessor.OUTPUT)
    MessageChannel output();
}
