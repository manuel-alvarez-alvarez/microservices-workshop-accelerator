package es.malvarez.microservices.web.repository;

import es.malvarez.microservices.web.domain.Collision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository to store the collisions in a database :)
 */
@Repository
public interface CollisionRepository extends JpaRepository<Collision, UUID> {
}
