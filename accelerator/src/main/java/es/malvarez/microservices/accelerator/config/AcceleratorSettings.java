package es.malvarez.microservices.accelerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Settings for the accelerator
 */
@Component
@ConfigurationProperties(prefix = "accelerator")
public class AcceleratorSettings {

    private long collisionInterval = 1000;

    public long getCollisionInterval() {
        return collisionInterval;
    }

    public void setCollisionInterval(final long collisionInterval) {
        this.collisionInterval = collisionInterval;
    }
}
