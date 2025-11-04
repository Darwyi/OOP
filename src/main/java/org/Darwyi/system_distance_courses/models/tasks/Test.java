package org.Darwyi.system_distance_courses.models.tasks;

import java.util.List;

class Test extends Task {
    private String testURL;

    public Test(String topic, String description, String attachment, String testURL, Double maxTestScore) {
        super(topic, description, attachment, TaskType.TEST, maxTestScore);
        this.testURL = testURL;
    }

    public String getTestURL() {
        return testURL;
    }

    public void setTestURL(String testURL) {
        this.testURL = testURL;
    }

    public static Double averageScore(List<Test> tests) {

        double sum = 0;
        for (Test s : tests) {
            sum += s.getMark();
        }
        return sum / tests.size();
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
