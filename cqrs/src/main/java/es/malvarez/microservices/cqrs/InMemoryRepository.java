package es.malvarez.microservices.cqrs;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Yep as you can guess we will store them in memory ;)
 */
public class InMemoryRepository<A extends IAggregate> implements IAggregateRepository<A> {

    private final Class<A> target;
    private final MessageChannel eventStore;

    private final Map<String, A> storage = new LinkedHashMap<>();
    private final Set<Class<? extends IEvent>> supportedEvents;

    @SuppressWarnings("unchecked")
    public InMemoryRepository(final Class<A> target, final EventStoreProcessor eventStore) {
        this.target = target;
        this.eventStore = eventStore.output();
        this.supportedEvents = Arrays.stream(ReflectionUtils.getAllDeclaredMethods(this.target))
                .filter(it -> it.getParameterTypes().length == 1 && IEvent.class.isAssignableFrom(it.getParameterTypes()[0]))
                .map(it -> (Class<? extends IEvent>) it.getParameterTypes()[0])
                .collect(Collectors.toSet());
    }

    @Override
    public A load(final String id) {
        return storage.computeIfAbsent(id, this::newInstance);
    }

    @Override
    public void save(final A aggregate, final List<? extends IEvent> list) {
        list.forEach(this::saveEvent);
    }

    @StreamListener(EventStoreProcessor.INPUT)
    public void onEvent(final IEvent event) {
        if (this.supportedEvents.contains(event.getClass())) { // I know, no inheritance et blah blah blah ...
            A aggregate = load(event.getAggregateId());
            invokeListenerMethod(aggregate, event);
        }
    }

    private A newInstance(String id) {
        try {
            return target.newInstance();
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Give me a public no args contructor mate!", e);
        }
    }

    private void invokeListenerMethod(final A aggregate, final IEvent event) {
        Method method = ReflectionUtils.findMethod(
                target,
                String.format("on%s", event.getClass().getSimpleName()),
                event.getClass()
        );
        if (method != null) {
            ReflectionUtils.makeAccessible(method);
            ReflectionUtils.invokeMethod(method, aggregate, event);
        }
    }

    private <E extends IEvent> void saveEvent(final E event) {
        eventStore.send(MessageBuilder.withPayload(event)
                .setHeader(Headers.AGGREGATE.name(), target.getName())
                .setHeader(Headers.EVENT_TYPE.name(), event.getClass().getName())
                .build());
    }
}
