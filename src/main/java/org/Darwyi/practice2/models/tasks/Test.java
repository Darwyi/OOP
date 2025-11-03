package org.Darwyi.practice2.models.tasks;

class Test extends Task {
    private String testURL;

    public Test(String topic, String description, String attachment, String testURL) {
        super(topic, description, attachment, TaskType.TEST);
        this.testURL = testURL;
    }

    public String getTestURL() {
        return testURL;
    }

    public void setTestURL(String testURL) {
        this.testURL = testURL;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + getId() +
                ", topic='" + getTopic() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", attachment='" + getAttachment() + '\'' +
                ", testURL='" + getTestURL() + '\'' +
                ", type=" + getType() +
                '}';
    }
}
