package es.malvarez.microservices.api;

import java.math.BigDecimal;

/**
 * Our little babies
 */
public enum ParticleType {

    // Quarks
    QUARK_UP(Spin._1_2, Charge._2_3, new BigDecimal("2.4")),
    QUARK_DOWN(Spin._1_2, Charge._M_1_3, new BigDecimal("4.8")),
    QUARK_CHARM(Spin._1_2, Charge._2_3, new BigDecimal("1.275").multiply(new BigDecimal("1000"))),
    QUARK_STRANGE(Spin._1_2, Charge._M_1_3, new BigDecimal("95")),
    QUARK_TOP(Spin._1_2, Charge._2_3, new BigDecimal("172.44").multiply(new BigDecimal("1000"))),
    QUARK_BOTTOM(Spin._1_2, Charge._M_1_3, new BigDecimal("4.18").multiply(new BigDecimal("1000"))),

    // Leptons
    ELECTRON(Spin._1_2, Charge._M_1, new BigDecimal("0.511")),
    ELECTRON_NEUTRINO(Spin._1_2, Charge._0, new BigDecimal("0.0000022")),
    MUON(Spin._1_2, Charge._M_1, new BigDecimal("105.67")),
    MUON_NEUTRINO(Spin._1_2, Charge._0, new BigDecimal("1.7")),
    TAU(Spin._1_2, Charge._M_1, new BigDecimal("1.7768").multiply(new BigDecimal("1000"))),
    TAU_NEUTRINO(Spin._1_2, Charge._0, new BigDecimal("15.5")),

    // Bosons
    PHOTON(Spin._1, Charge._0, BigDecimal.ZERO),
    W_BOSON(Spin._1, Charge._MM_1, new BigDecimal("80.39").multiply(new BigDecimal("1000"))),
    Z_BOSON(Spin._1, Charge._0, new BigDecimal("91.19").multiply(new BigDecimal("1000"))),
    GLUON(Spin._1, Charge._0, BigDecimal.ZERO),
    HIGGS_BOSON(Spin._0, Charge._0, new BigDecimal("125.09").multiply(new BigDecimal("1000"))),

    // Dark magi... matter!
    CHAMELEON(Spin._0, Charge._0, null);

    public final Spin spin;
    public final Charge charge;
    public final BigDecimal massInMevC2;

    ParticleType(final Spin spin, final Charge charge, final BigDecimal massInMevC2) {
        this.spin = spin;
        this.charge = charge;
        this.massInMevC2 = massInMevC2;
    }
}
