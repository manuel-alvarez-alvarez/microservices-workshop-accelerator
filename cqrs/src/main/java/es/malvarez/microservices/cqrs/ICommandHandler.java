package es.malvarez.microservices.cqrs;

/**
 * So this is the basic for a command handler.
 */
@FunctionalInterface
public interface ICommandHandler<C extends ICommand> {

    void handle(final C command);
}
