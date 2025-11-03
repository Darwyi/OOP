package org.Darwyi.practice2.models.tasks;

class Topic extends Task {
    private boolean isLecture;

    Topic(String topic, String description, String attachment) {
        super(topic, description, attachment, TaskType.TOPIC);
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
