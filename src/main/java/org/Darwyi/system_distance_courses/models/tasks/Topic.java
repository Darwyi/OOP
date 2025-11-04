package org.Darwyi.system_distance_courses.models.tasks;

class Topic extends Task {
    private boolean isLecture;

    Topic(String topic, String description, String attachment, boolean isLecture) {
        super(topic, description, attachment, TaskType.TOPIC);
        this.isLecture = isLecture;
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
