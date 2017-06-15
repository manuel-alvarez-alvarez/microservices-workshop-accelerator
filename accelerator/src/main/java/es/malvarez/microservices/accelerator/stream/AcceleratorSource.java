package es.malvarez.microservices.accelerator.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Source for events coming from the accelerator
 */
public interface AcceleratorSource {

    String OUTPUT = "accelerator_output";

    @Output(AcceleratorSource.OUTPUT)
    MessageChannel output();
}
