package es.malvarez.microservices.api;

import java.util.EnumSet;

import static es.malvarez.microservices.api.ParticleType.*;


/**
 * Charges of the particles
 */
public enum Charge {

    /**
     * 2/3
     */
    _2_3(EnumSet.of(QUARK_UP, QUARK_CHARM, QUARK_TOP)),

    /**
     * 0
     */
    _0(EnumSet.of(ELECTRON_NEUTRINO, MUON_NEUTRINO, TAU_NEUTRINO, GLUON, Z_BOSON, HIGGS_BOSON, CHAMELEON)),

    /**
     * -1/3
     */
    _M_1_3(EnumSet.of(QUARK_DOWN, QUARK_STRANGE, QUARK_BOTTOM)),

    /**
     * -1
     */
    _M_1(EnumSet.of(ELECTRON, MUON, TAU)),

    /**
     * ±1
     */
    _MM_1(EnumSet.of(W_BOSON));

    public final EnumSet<ParticleType> particles;

    Charge(EnumSet<ParticleType> particles) {
        this.particles = particles;
    }

}
