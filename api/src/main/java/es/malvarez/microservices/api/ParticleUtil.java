package es.malvarez.microservices.api;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Some utilities ...
 */
public abstract class ParticleUtil {

    @FunctionalInterface
    private interface ParticleChecker {
        boolean isParticle(Spin spin, Charge charge, BigDecimal massInMevC2);
    }

    @FunctionalInterface
    private interface ParticleBuilder {
        DetectedParticle build(final Experiment experiment);
    }

    private static final Map<ParticleType, ParticleChecker> CHECKER;
    private static final Map<ParticleType, ParticleBuilder> BUILDER;
    private static final Random RANDOM;

    private ParticleUtil() {
    }

    static {
        CHECKER = new EnumMap<>(ParticleType.class);
        BUILDER = new EnumMap<>(ParticleType.class);
        Arrays.stream(ParticleType.values()).forEach(type -> {
            if (type == ParticleType.CHAMELEON) {
                CHECKER.put(type, ParticleUtil::isChameleon);
                BUILDER.put(type, ParticleUtil::nextChameleon);
            } else {
                CHECKER.put(type, ParticleUtil.isParticle(type));
                BUILDER.put(type, ParticleUtil.nextParticle(type));
            }
        });
        RANDOM = new SecureRandom();
    }

    public static boolean assertType(final DetectedParticle particle, final ParticleType type) {
        return assertType(particle.getSpin(), particle.getCharge(), particle.getMassInMevC2(), type);
    }

    public static boolean assertType(final Spin spin, final Charge charge, final BigDecimal massInMevC2, final ParticleType type) {
        Set<ParticleType> found = Arrays.stream(ParticleType.values())
                .filter(t -> CHECKER.get(t).isParticle(spin, charge, massInMevC2))
                .collect(Collectors.toSet());
        return found.size() > 0 && found.contains(type);
    }

    public static DetectedParticle build(final ParticleType type, final Experiment experiment) {
        return BUILDER.get(type).build(experiment);
    }

    private static ParticleChecker isParticle(final ParticleType type) {
        return (spin, charge, massInMevC2) -> charge.equals(type.charge)
                && spin.equals(type.spin)
                && massInMevC2.equals(type.massInMevC2);
    }

    private static boolean isChameleon(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._0)
                && massInMevC2.compareTo(BigDecimal.valueOf(125_000)) <= 0
                && massInMevC2.compareTo(BigDecimal.ZERO) >= 0;
    }

    private static ParticleBuilder nextParticle(final ParticleType type) {
        return experiment -> new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setMassInMevC2(type.massInMevC2)
                .setCharge(type.charge)
                .setSpin(type.spin)
                .build();
    }

    private static DetectedParticle nextChameleon(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setMassInMevC2(BigDecimal.valueOf(RANDOM.nextDouble() * 125_000))
                .setCharge(Charge._0)
                .setSpin(Spin._0)
                .build();
    }
}
