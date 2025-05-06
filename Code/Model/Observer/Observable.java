package Model.Observer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public interface Observable {
    List<Observer> getObservers();

    default void addObserver(Observer observer) {
        if (observer != null && !getObservers().contains(observer)) {
            getObservers().add(observer);
        }
    }

    default void removeObserver(Observer observer) {
        getObservers().remove(observer);
    }

    default void notifyObservers(EventType eventType, Object data) {
        Event event = new Event(this, eventType, data);
        for (Observer observer : getObservers()) {
            if (observer.accepts(eventType)) {
                observer.onEvent(event);
            }
        }
    }
}
