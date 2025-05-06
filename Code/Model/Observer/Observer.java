package Model.Observer;

public interface Observer {
    void onEvent(Event event);

    default boolean accepts(EventType eventType) {
        return true;
    }
}
