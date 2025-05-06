package Model.Observer;

public class Event {
    private final Object source;
    private final EventType eventType;
    private final Object data;
    private final Class<?> sourceType;

    public Event(Object source, EventType eventType, Object data) {
        this.source = source;
        this.eventType = eventType;
        this.data = data;
        this.sourceType = source != null ? source.getClass() : null;
    }

    // Getterek
    public Object getSource() { return source; }
    public EventType getEventType() { return eventType; }
    public Object getData() { return data; }
    public Class<?> getSourceType() { return sourceType; }
}
