package org.Darwyi.courseProject.observer;

import java.util.EnumMap;
import java.util.Map;

public class StatisticsCollector implements EventListener {
    private final Map<EventType, Integer> counts = new EnumMap<>(EventType.class);
    private int total = 0;

    @Override
    public void onEvent(Event event){
        counts.merge(event.getType(), 1, Integer::sum);
        total++;
    }

    public int getTotal(){ return total; }
    public int countOf(EventType type){ return counts.getOrDefault(type, 0); }
    public Map<EventType, Integer> getCounts(){ return new EnumMap<>(counts); }

    public String report(){
        StringBuilder sb = new StringBuilder();
        sb.append("Усього подій: ").append(total).append('\n');
        for (EventType t : EventType.values()){
            int c = counts.getOrDefault(t, 0);
            if (c > 0) sb.append("  %-26s : %d%n".formatted(t.label(), c));
        }
        return sb.toString();
    }
}
