package es.malvarez.microservices.accelerator.service;

import es.malvarez.microservices.api.DetectedParticle;
import es.malvarez.microservices.api.Snapshot;
import es.malvarez.microservices.accelerator.stream.AcceleratorSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service to connect the app with the accelerator stream
 */
@Service
public class StreamService {

    private final AcceleratorSource stream;
    private final MessageChannel errorChannel;

    @Autowired
    protected StreamService(
            final AcceleratorSource stream,
            @Qualifier(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME) final MessageChannel errorChannel
    ) {
        this.stream = stream;
        this.errorChannel = errorChannel;
    }

    public void sendMessage(final Snapshot snapshot) {
        try {
            Snapshot untypedSnapshot = new Snapshot.Builder()
                    .setId(snapshot.getId())
                    .setWhen(snapshot.getWhen())
                    .addParticles(snapshot.getParticles().stream().map(this::removeType).collect(Collectors.toList()))
                    .build();
            stream.output().send(MessageBuilder.withPayload(untypedSnapshot).build());
        } catch (final Throwable e) {
            errorChannel.send(new ErrorMessage(e));
        }
    }

    private DetectedParticle removeType(final DetectedParticle particle) {
        return new DetectedParticle.Builder()
                .setId(particle.getId())
                .setMassInMevC2(particle.getMassInMevC2())
                .setCharge(particle.getCharge())
                .setSpin(particle.getSpin())
                .setExperiment(particle.getExperiment())
                .build();
    }

}
