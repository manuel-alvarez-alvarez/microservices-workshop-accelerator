package es.malvarez.microservices.cqrs;

/**
 * So these are the headers to include in our events
 */
public enum Headers {

    /**
     * This is the FQDCN of the aggregate class
     */
    AGGREGATE,

    /**
     * This is the FQDCN of the event
     */
    EVENT_TYPE
}
