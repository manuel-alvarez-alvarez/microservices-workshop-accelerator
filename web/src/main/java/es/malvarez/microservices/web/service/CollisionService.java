package es.malvarez.microservices.web.service;

import es.malvarez.microservices.web.domain.Collision;
import es.malvarez.microservices.web.repository.CollisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

/**
 * Service to connect the detector with the web
 */
@Service
public class CollisionService {

    private final CollisionRepository collisionRepository;
    private final SimpMessagingTemplate simpleMessagingTemplate;
    private final MessageChannel errorChannel;

    @Autowired
    public CollisionService(
            final CollisionRepository collisionRepository,
            final SimpMessagingTemplate simpleMessagingTemplate,
            @Qualifier(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME) final MessageChannel errorChannel
    ) {
        this.collisionRepository = collisionRepository;
        this.simpleMessagingTemplate = simpleMessagingTemplate;
        this.errorChannel = errorChannel;
    }

    @Transactional(readOnly = true)
    public Page<Collision> findAll(final Pageable page) {
        return collisionRepository.findAll(page);
    }

    @Transactional(readOnly = true)
    public Collision find(final UUID id) {
        return collisionRepository.findOne(id);
    }

    @Transactional
    public void newCollision(final Collision collision) {
        collisionRepository.save(collision);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                notifyClients(collision);
            }
        });
    }

    private void notifyClients(final Collision collision) {
        try {
            simpleMessagingTemplate.convertAndSend("/topic/collision", collision.getId());
        } catch (final Throwable e) {
            errorChannel.send(new ErrorMessage(e));
        }
    }
}
