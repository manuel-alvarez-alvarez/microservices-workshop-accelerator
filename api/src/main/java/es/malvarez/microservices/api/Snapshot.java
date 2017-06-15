package es.malvarez.microservices.api;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * So every second we get a snapshot of the particles in our experiments ...
 */
public class Snapshot {

    private final UUID id;
    private final Date when;
    private final List<DetectedParticle> particles;

    private Snapshot(final UUID id, final Date when, final List<DetectedParticle> particles) {
        this.id = id;
        this.when = when;
        this.particles = particles;
    }

    public UUID getId() {
        return id;
    }

    public Date getWhen() {
        return when;
    }

    public List<DetectedParticle> getParticles() {
        return particles;
    }

    public static class Builder {
        private UUID id;
        private Date when;
        private final List<DetectedParticle> particles = new LinkedList<>();

        public Builder setId(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder setRandomId() {
            return setId(UUID.randomUUID());
        }

        public Builder setWhen(final Date when) {
            this.when = when;
            return this;
        }

        public Builder setNow() {
            return setWhen(new Date());
        }

        public Builder addParticles(final List<DetectedParticle> particles) {
            this.particles.addAll(particles);
            return this;
        }

        public Snapshot build() {
            return new Snapshot(id, when, particles);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Snapshot snapshot = (Snapshot) o;

        return id != null ? id.equals(snapshot.id) : snapshot.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
