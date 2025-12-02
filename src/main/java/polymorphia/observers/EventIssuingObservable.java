package polymorphia.observers;

import java.util.List;

import polymorphia.EventType;

public interface EventIssuingObservable {
    void attach(EventObserver observer);

    void attach(EventObserver observer, EventType type);

    void attach(EventObserver observer, List<EventType> types);

    void detach(EventObserver observer);
}
