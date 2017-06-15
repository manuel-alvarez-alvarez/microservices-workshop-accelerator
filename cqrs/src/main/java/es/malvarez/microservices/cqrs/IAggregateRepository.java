package es.malvarez.microservices.cqrs;

import java.util.List;

/**
 * So ... we are saving aggregates right?
 */
public interface IAggregateRepository<A extends IAggregate> {

    A load(String aggregate);

    void save(A aggregate, List<? extends IEvent> events);
}
