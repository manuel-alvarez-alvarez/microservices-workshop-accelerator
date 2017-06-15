package es.malvarez.microservices.cqrs;

/**
 * Something has been done!
 */
public interface IEvent {

    String getAggregateId();

}
