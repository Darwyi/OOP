package org.Darwyi.practice2.models.tasks;

import org.Darwyi.practice2.Storage;
import org.Darwyi.practice2.models.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Task {
    protected Long id;
    protected String topic;
    protected String description;
    protected String attachment;
    private int mark;
    private boolean Completed;
    private TaskType type;

    public Task(String topic, String description, String attachment, TaskType type) {
        id = Math.abs(new Random().nextLong());
        this.topic = topic;
        this.description = description;
        this.attachment = attachment;
        this.type = type;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public boolean isCompleted() {
        if (mark > 0) {
            Completed = true;
        }
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void useAgain() {}

    public Map<Long, Integer> getResults() {
        Map <Long, Integer> results = new HashMap<>();
        for (Student s : Storage.Students ) {
            if (isCompleted()){
                results.put(s.getId(), this.getMark());
            }
        }
        return results;
    }

    public Integer getResultsForStudent(Long id) {
        if (Storage.Students.contains(Storage.getStudentById(id))) {
            if (isCompleted()){
                return this.getMark();
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", topic='" + getTopic() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", type=" + getType() +
                '}';
    }
}
