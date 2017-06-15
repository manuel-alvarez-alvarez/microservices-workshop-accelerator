package es.malvarez.microservices.accelerator.service;

import es.malvarez.microservices.api.Experiment;
import es.malvarez.microservices.api.ParticleType;
import es.malvarez.microservices.api.ParticleUtil;
import es.malvarez.microservices.api.Snapshot;
import es.malvarez.microservices.accelerator.config.AcceleratorSettings;
import es.malvarez.microservices.accelerator.util.AcceleratorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is the main accelerator service
 */
@Service
public class AcceleratorService {


    private final StreamService streamService;
    private final WebSocketService webSocketService;
    private final ThreadPoolTaskScheduler acceleratorExecutor;
    private final Random randomizer;
    private final AcceleratorSettings acceleratorSettings;

    private volatile ScheduledFuture<?> future = null;

    @Autowired
    public AcceleratorService(
            final StreamService streamService,
            final WebSocketService webSocketService,
            final ThreadPoolTaskScheduler acceleratorExecutor,
            final Random randomizer,
            final AcceleratorSettings acceleratorSettings
    ) {
        this.streamService = streamService;
        this.webSocketService = webSocketService;
        this.acceleratorExecutor = acceleratorExecutor;
        this.randomizer = randomizer;
        this.acceleratorSettings = acceleratorSettings;
    }

    public AcceleratorStatus getStatus() {
        return future != null ? AcceleratorStatus.ON : AcceleratorStatus.OFF;
    }

    public synchronized void toggle() {
        if (future == null) {
            future = acceleratorExecutor.scheduleAtFixedRate(this::nextSnapshot, acceleratorSettings.getCollisionInterval());
            webSocketService.sendMessage(AcceleratorStatus.ON);
        } else {
            future.cancel(true);
            future = null;
            webSocketService.sendMessage(AcceleratorStatus.OFF);
        }
    }

    private void nextSnapshot() {
        Snapshot snapshot = new Snapshot.Builder()
                .setRandomId()
                .setNow()
                .addParticles(
                        Arrays.stream(Experiment.values())
                                .flatMap(experiment -> nextRange().mapToObj(index -> ParticleUtil.build(nextParticle(), experiment)))
                                .collect(Collectors.toList()))
                .build();
        webSocketService.sendMessage(snapshot);
        streamService.sendMessage(snapshot);
    }

    private IntStream nextRange() {
        return IntStream.range(0, nextNumParticles());
    }

    private int nextNumParticles() {
        return randomizer.nextInt(6);
    }

    private ParticleType nextParticle() {
        ParticleType[] values = ParticleType.values();
        return values[randomizer.nextInt(values.length)];
    }


}
