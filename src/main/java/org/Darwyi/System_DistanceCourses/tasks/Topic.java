package org.Darwyi.System_DistanceCourses.tasks;

import java.util.Map;

class Topic extends Task {
    private boolean isLecture;

    Topic(String topic, String description, String attachment, boolean isLecture) {
        super(topic, description, attachment, TaskType.TOPIC);
        this.isLecture = isLecture;
    }

    @Override
    public Map<Long, Double> getResults() throws Exception {
        return null;
    }

    @Override
    public Double getResultsForStudent(Long id) {
        return 0.0;
    }

    public boolean isLecture() {
        if (isLecture) {
            super.setCompleted(true);
        }
        return isLecture;
    }

    public void setLecture(boolean lecture) {
        isLecture = lecture;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + getId() +
                ", topic='" + getTopic() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", attachment='" + getAttachment() + '\'' +
                ", isLecture=" + isLecture() +
                ", type=" + getType() +
                '}';
    }
}
