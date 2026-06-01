package org.Darwyi.courseProject.observer;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener){
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void unsubscribe(EventListener listener){
        listeners.remove(listener);
    }

    public void publish(Event event){
        for (EventListener l : listeners) l.onEvent(event);
    }
}
