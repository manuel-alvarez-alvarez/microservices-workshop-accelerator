package es.malvarez.microservices.cqrs;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * So this is the events broadcasting channel, subscribe and have found with the latest news or use it
 * to spread our seed around the world
 */
public interface BroadcastSink {

    String INPUT = "event_broadcast_input";

    @Input(BroadcastSink.INPUT)
    SubscribableChannel input();
}
