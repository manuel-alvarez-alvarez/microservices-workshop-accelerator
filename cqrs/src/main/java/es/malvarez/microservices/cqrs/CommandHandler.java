package es.malvarez.microservices.cqrs;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Base class for command handlers.
 */
public class CommandHandler<C extends ICommand, A extends IAggregate> implements ICommandHandler<C> {

    private final BiFunction<A, C, List<? extends IEvent>> invoker;
    private final IAggregateRepository<A> aggregateRepository;

    public CommandHandler(final BiFunction<A, C, List<? extends IEvent>> invoker, final IAggregateRepository<A> aggregateRepository) {
        this.invoker = invoker;
        this.aggregateRepository = aggregateRepository;
    }

    @Override
    public void handle(final C command) {
        A aggregate = aggregateRepository.load(command.getAggregateId());
        List<? extends IEvent> events = invoker.apply(aggregate, command);
        aggregateRepository.save(aggregate, events);
    }
}
