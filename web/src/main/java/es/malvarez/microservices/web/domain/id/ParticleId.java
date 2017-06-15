package es.malvarez.microservices.web.domain.id;

import java.io.Serializable;
import java.util.UUID;

/**
 * Collision id class
 */
public class ParticleId implements Serializable {

    private UUID id;
    private UUID collision;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCollision() {
        return collision;
    }

    public void setCollision(UUID collision) {
        this.collision = collision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticleId that = (ParticleId) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return collision != null ? collision.equals(that.collision) : that.collision == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (collision != null ? collision.hashCode() : 0);
        return result;
    }
}
