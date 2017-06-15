package es.malvarez.microservices.api;


import java.math.BigDecimal;
import java.util.UUID;

/**
 * Finally we are starting to see something ;)
 */
public class DetectedParticle {

    private final Experiment experiment;
    private final UUID id;
    private final ParticleType type;
    private final Spin spin;
    private final Charge charge;
    private final BigDecimal massInMevC2;

    private DetectedParticle(final UUID id, final Experiment experiment, final ParticleType type, final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        this.id = id;
        this.experiment = experiment;
        this.type = type;
        this.spin = spin;
        this.charge = charge;
        this.massInMevC2 = massInMevC2;
    }

    public UUID getId() {
        return id;
    }

    public ParticleType getType() {
        return type;
    }

    public Spin getSpin() {
        return spin;
    }

    public Charge getCharge() {
        return charge;
    }

    public BigDecimal getMassInMevC2() {
        return massInMevC2;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DetectedParticle particle = (DetectedParticle) o;

        return id != null ? id.equals(particle.id) : particle.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static class Builder {
        private UUID id;
        private ParticleType type;
        private Spin spin;
        private Charge charge;
        private BigDecimal massInMevC2;
        private Experiment experiment;

        public Builder setId(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder setRandomId() {
            return setId(UUID.randomUUID());
        }

        public Builder setType(final ParticleType type) {
            this.type = type;
            return this;
        }

        public Builder setSpin(final Spin spin) {
            this.spin = spin;
            return this;
        }

        public Builder setCharge(final Charge charge) {
            this.charge = charge;
            return this;
        }

        public Builder setMassInMevC2(final BigDecimal massInMevC2) {
            this.massInMevC2 = massInMevC2;
            return this;
        }

        public Builder setExperiment(Experiment experiment) {
            this.experiment = experiment;
            return this;
        }

        public Builder from(final DetectedParticle particle) {
            this.id = particle.getId();
            this.type = particle.getType();
            this.spin = particle.getSpin();
            this.charge = particle.getCharge();
            this.massInMevC2 = particle.getMassInMevC2();
            this.experiment = particle.getExperiment();
            return this;
        }

        public DetectedParticle build() {
            return new DetectedParticle(id, experiment, type, spin, charge, massInMevC2);
        }
    }
}
