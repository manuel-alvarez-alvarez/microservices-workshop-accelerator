package es.malvarez.microservices.api;

import java.util.EnumSet;

import static es.malvarez.microservices.api.ParticleType.*;

/**
 * Spins for the particles
 */
public enum Spin {

    /**
     * 1/2
     */
    _1_2(EnumSet.of(QUARK_UP, QUARK_DOWN, QUARK_CHARM, QUARK_STRANGE, QUARK_TOP, QUARK_BOTTOM, ELECTRON, ELECTRON_NEUTRINO, MUON, MUON_NEUTRINO, TAU, TAU_NEUTRINO)),

    /**
     * 0
     */
    _0(EnumSet.of(HIGGS_BOSON, CHAMELEON)),

    /**
     * 1
     */
    _1(EnumSet.of(GLUON, Z_BOSON, W_BOSON));

    public final EnumSet<ParticleType> particles;

    Spin(EnumSet<ParticleType> particles) {
        this.particles = particles;
    }
}
