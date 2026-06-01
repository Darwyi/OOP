package org.Darwyi.courseProject.observer;

public class Event {
    private final EventType type;
    private final String message;
    private final long entityId; // напр., id курсу
    private final long actorId;  // напр., id студента/викладача
    private final long timestamp;

    public Event(EventType type, String message, long entityId, long actorId){
        this.type = type;
        this.message = message;
        this.entityId = entityId;
        this.actorId = actorId;
        this.timestamp = System.currentTimeMillis();
    }

    public EventType getType(){ return type; }
    public String getMessage(){ return message; }
    public long getEntityId(){ return entityId; }
    public long getActorId(){ return actorId; }
    public long getTimestamp(){ return timestamp; }
}
