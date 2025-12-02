package polymorphia.observers;

import polymorphia.EventType;

public interface EventObserver {
    void update(EventType event, String message);
}
