package org.Darwyi.courseProject.observer;

import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.storage.DataStore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NotificationService implements EventListener {
    private final DataStore store;
    private final Map<Long, List<String>> inbox = new LinkedHashMap<>();

    public NotificationService(DataStore store){
        this.store = store;
    }

    @Override
    public void onEvent(Event event){
        switch (event.getType()){
            case MATERIAL_ADDED -> notifyEnrolled(event.getEntityId(),
                    "Новий матеріал у курсі: " + event.getMessage());
            case COURSE_ACTIVATED -> notifyEnrolled(event.getEntityId(),
                    "Курс активовано: " + event.getMessage());
            case CERTIFICATE_ISSUED -> push(event.getActorId(),
                    "Вітаємо! Видано " + event.getMessage());
            default -> { /* інші події сповіщень не породжують */ }
        }
    }

    private void notifyEnrolled(long courseId, String text){
        Course c = store.getCourses().get(courseId);
        if (c == null) return;
        for (Long sid : c.getEnrolledStudentIds()) push(sid, text);
    }

    private void push(long studentId, String text){
        inbox.computeIfAbsent(studentId, k -> new ArrayList<>()).add(text);
    }

    public List<String> inboxOf(long studentId){
        return List.copyOf(inbox.getOrDefault(studentId, List.of()));
    }
}
