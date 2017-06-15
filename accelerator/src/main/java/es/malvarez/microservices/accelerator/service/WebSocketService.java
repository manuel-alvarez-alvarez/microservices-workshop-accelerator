package es.malvarez.microservices.accelerator.service;

import es.malvarez.microservices.api.Snapshot;
import es.malvarez.microservices.accelerator.util.AcceleratorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * Service to connect the accelerator with the web
 */
@Service
public class WebSocketService {

    private final SimpMessagingTemplate simpleMessagingTemplate;
    private final MessageChannel errorChannel;


    @Autowired
    public WebSocketService(
            final SimpMessagingTemplate simpleMessagingTemplate,
            @Qualifier(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME) final MessageChannel errorChannel
    ) {
        this.simpleMessagingTemplate = simpleMessagingTemplate;
        this.errorChannel = errorChannel;
    }

    public void sendMessage(final Snapshot snapshot) {
        try {
            simpleMessagingTemplate.convertAndSend("/topic/snapshot", snapshot);
        } catch (final Throwable e) {
            errorChannel.send(new ErrorMessage(e));
        }
    }

    public void sendMessage(final AcceleratorStatus status) {
        try {
            simpleMessagingTemplate.convertAndSend("/topic/status", status);
        } catch (final Throwable e) {
            errorChannel.send(new ErrorMessage(e));
        }
    }
}
