package org.Darwyi.system_distance_courses.models.tasks;

import org.Darwyi.system_distance_courses.Storage;
import org.Darwyi.system_distance_courses.models.usermodels.User;
import org.Darwyi.system_distance_courses.models.usermodels.UserRole;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Task {
    protected Long id;
    protected String topic;
    protected String description;
    protected String attachment;
    private Double mark;
    private Double maxMark;
    private Boolean Completed;
    private TaskType type;

    public Task(String topic, String description, String attachment, TaskType type, Double maxMark) {
        id = Math.abs(new Random().nextLong());
        this.topic = topic;
        this.description = description;
        this.attachment = attachment;
        this.type = type;
        this.maxMark = maxMark;
        Storage.Tasks.add(this);
    }

    public Task(String topic, String description, String attachment, TaskType type) {
        id = Math.abs(new Random().nextLong());
        this.topic = topic;
        this.description = description;
        this.attachment = attachment;
        this.type = type;
        Storage.Tasks.add(this);
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Boolean isCompleted() {
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

    public Double getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Double maxMark) {
        this.maxMark = maxMark;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void useAgain() {}

    public Map<Long, Double> getResults() throws Exception {
        Map <Long, Double> results = new HashMap<>();
        for (User s : Storage.Users ) {
            if (s.getRole() != UserRole.STUDENT) {
                throw new Exception("Only students have results for tasks");
            }
            if (isCompleted()){
                results.put(s.getId(), this.getMark());
            }
        }
        return results;
    }

    public Double getResultsForStudent(Long id) {
        User student = Storage.getUserById(id);
        if (student != null && student.getRole() == UserRole.STUDENT) {
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
