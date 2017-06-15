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
        CHECKER.put(ParticleType.QUARK_UP, ParticleUtil::isQuarkUp);
        CHECKER.put(ParticleType.QUARK_DOWN, ParticleUtil::isQuarkDown);
        CHECKER.put(ParticleType.QUARK_CHARM, ParticleUtil::isQuarkCharm);
        CHECKER.put(ParticleType.QUARK_STRANGE, ParticleUtil::isQuarkStrange);
        CHECKER.put(ParticleType.QUARK_TOP, ParticleUtil::isQuarkTop);
        CHECKER.put(ParticleType.QUARK_BOTTOM, ParticleUtil::isQuarkBottom);
        CHECKER.put(ParticleType.ELECTRON, ParticleUtil::isElectron);
        CHECKER.put(ParticleType.ELECTRON_NEUTRINO, ParticleUtil::isElectronNeutrino);
        CHECKER.put(ParticleType.MUON, ParticleUtil::isMuon);
        CHECKER.put(ParticleType.MUON_NEUTRINO, ParticleUtil::isMuonNeutrino);
        CHECKER.put(ParticleType.TAU, ParticleUtil::isTau);
        CHECKER.put(ParticleType.TAU_NEUTRINO, ParticleUtil::isTauNeutrino);
        CHECKER.put(ParticleType.PHOTON, ParticleUtil::isPhoton);
        CHECKER.put(ParticleType.W_BOSON, ParticleUtil::isWBoson);
        CHECKER.put(ParticleType.Z_BOSON, ParticleUtil::isZBoson);
        CHECKER.put(ParticleType.GLUON, ParticleUtil::isGluon);
        CHECKER.put(ParticleType.HIGGS_BOSON, ParticleUtil::isHiggsBoson);
        CHECKER.put(ParticleType.CHAMELEON, ParticleUtil::isChameleon);

        BUILDER = new EnumMap<>(ParticleType.class);
        BUILDER.put(ParticleType.QUARK_UP, ParticleUtil::nextQuarkUp);
        BUILDER.put(ParticleType.QUARK_DOWN, ParticleUtil::nextQuarkDown);
        BUILDER.put(ParticleType.QUARK_CHARM, ParticleUtil::nextQuarkCharm);
        BUILDER.put(ParticleType.QUARK_STRANGE, ParticleUtil::nextQuarkStrange);
        BUILDER.put(ParticleType.QUARK_TOP, ParticleUtil::nextQuarkTop);
        BUILDER.put(ParticleType.QUARK_BOTTOM, ParticleUtil::nextQuarkBottom);
        BUILDER.put(ParticleType.ELECTRON, ParticleUtil::nextElectron);
        BUILDER.put(ParticleType.ELECTRON_NEUTRINO, ParticleUtil::nextElectronNeutrino);
        BUILDER.put(ParticleType.MUON, ParticleUtil::nextMuon);
        BUILDER.put(ParticleType.MUON_NEUTRINO, ParticleUtil::nextMuonNeutrino);
        BUILDER.put(ParticleType.TAU, ParticleUtil::nextTau);
        BUILDER.put(ParticleType.TAU_NEUTRINO, ParticleUtil::nextTauNeutrino);
        BUILDER.put(ParticleType.PHOTON, ParticleUtil::nextPhoton);
        BUILDER.put(ParticleType.W_BOSON, ParticleUtil::nextWBoson);
        BUILDER.put(ParticleType.Z_BOSON, ParticleUtil::nextZBoson);
        BUILDER.put(ParticleType.GLUON, ParticleUtil::nextGluon);
        BUILDER.put(ParticleType.HIGGS_BOSON, ParticleUtil::nextHiggsBoson);
        BUILDER.put(ParticleType.CHAMELEON, ParticleUtil::nextChameleon);

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

    private static boolean isQuarkUp(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._2_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("2.4"));
    }

    private static boolean isQuarkDown(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("4.8"));
    }

    private static boolean isQuarkCharm(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._2_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("1.275").multiply(new BigDecimal("1000")));
    }

    private static boolean isQuarkStrange(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("95"));
    }

    private static boolean isQuarkTop(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._2_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("172.44").multiply(new BigDecimal("1000")));
    }

    private static boolean isQuarkBottom(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1_3)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("4.18").multiply(new BigDecimal("1000")));
    }

    private static boolean isElectron(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("0.511"));
    }

    private static boolean isElectronNeutrino(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("0.0000022"));
    }

    private static boolean isMuon(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("105.67"));
    }

    private static boolean isMuonNeutrino(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("1.7"));
    }

    private static boolean isTau(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._M_1)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("1.7768"));
    }

    private static boolean isTauNeutrino(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1_2)
                && massInMevC2.equals(new BigDecimal("15.5"));
    }

    private static boolean isPhoton(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1)
                && massInMevC2.equals(BigDecimal.ZERO);
    }

    private static boolean isWBoson(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._MM_1)
                && spin.equals(Spin._1)
                && massInMevC2.equals(new BigDecimal("80.39").multiply(new BigDecimal("1000")));
    }

    private static boolean isZBoson(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1)
                && massInMevC2.equals(new BigDecimal("91.19").multiply(new BigDecimal("1000")));
    }

    private static boolean isGluon(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._1)
                && massInMevC2.equals(BigDecimal.ZERO);
    }

    private static boolean isHiggsBoson(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._0)
                && massInMevC2.equals(new BigDecimal("125.09").multiply(new BigDecimal("1000")));
    }

    private static boolean isChameleon(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        return charge.equals(Charge._0)
                && spin.equals(Spin._0)
                && massInMevC2.compareTo(BigDecimal.valueOf(125_000)) <= 0
                && massInMevC2.compareTo(BigDecimal.ZERO) >= 0;
    }

    private static DetectedParticle nextQuarkUp(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_UP)
                .setMassInMevC2(new BigDecimal("2.4"))
                .setCharge(Charge._2_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextQuarkDown(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_DOWN)
                .setMassInMevC2(new BigDecimal("4.8"))
                .setCharge(Charge._M_1_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextQuarkCharm(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_CHARM)
                .setMassInMevC2(new BigDecimal("1.275").multiply(new BigDecimal("1000")))
                .setCharge(Charge._2_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextQuarkStrange(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_STRANGE)
                .setMassInMevC2(new BigDecimal("95"))
                .setCharge(Charge._M_1_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextQuarkTop(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_TOP)
                .setMassInMevC2(new BigDecimal("172.44").multiply(new BigDecimal("1000")))
                .setCharge(Charge._2_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextQuarkBottom(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.QUARK_BOTTOM)
                .setMassInMevC2(new BigDecimal("4.18").multiply(new BigDecimal("1000")))
                .setCharge(Charge._M_1_3)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextElectron(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.ELECTRON)
                .setMassInMevC2(new BigDecimal("0.511"))
                .setCharge(Charge._M_1)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextElectronNeutrino(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.ELECTRON_NEUTRINO)
                .setMassInMevC2(new BigDecimal("0.0000022"))
                .setCharge(Charge._0)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextMuon(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.MUON)
                .setMassInMevC2(new BigDecimal("105.67"))
                .setCharge(Charge._M_1)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextMuonNeutrino(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.MUON_NEUTRINO)
                .setMassInMevC2(new BigDecimal("1.7"))
                .setCharge(Charge._0)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextTau(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.TAU)
                .setMassInMevC2(new BigDecimal("1.7768"))
                .setCharge(Charge._M_1)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextTauNeutrino(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.TAU_NEUTRINO)
                .setMassInMevC2(new BigDecimal("15.5"))
                .setCharge(Charge._0)
                .setSpin(Spin._1_2)
                .build();
    }

    private static DetectedParticle nextPhoton(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.PHOTON)
                .setMassInMevC2(BigDecimal.ZERO)
                .setCharge(Charge._0)
                .setSpin(Spin._1)
                .build();
    }

    private static DetectedParticle nextWBoson(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.W_BOSON)
                .setMassInMevC2(new BigDecimal("80.39").multiply(new BigDecimal("1000")))
                .setCharge(Charge._MM_1)
                .setSpin(Spin._1)
                .build();
    }

    private static DetectedParticle nextZBoson(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.Z_BOSON)
                .setMassInMevC2(new BigDecimal("91.19").multiply(new BigDecimal("1000")))
                .setCharge(Charge._0)
                .setSpin(Spin._1)
                .build();
    }

    private static DetectedParticle nextGluon(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.GLUON)
                .setMassInMevC2(BigDecimal.ZERO)
                .setCharge(Charge._0)
                .setSpin(Spin._1)
                .build();
    }

    private static DetectedParticle nextHiggsBoson(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.HIGGS_BOSON)
                .setMassInMevC2(new BigDecimal("125.09").multiply(new BigDecimal("1000")))
                .setCharge(Charge._0)
                .setSpin(Spin._0)
                .build();
    }

    private static DetectedParticle nextChameleon(final Experiment experiment) {
        return new DetectedParticle.Builder()
                .setRandomId()
                .setExperiment(experiment)
                .setType(ParticleType.CHAMELEON)
                .setMassInMevC2(BigDecimal.valueOf(RANDOM.nextDouble() * 125_000))
                .setCharge(Charge._0)
                .setSpin(Spin._0)
                .build();
    }
}
