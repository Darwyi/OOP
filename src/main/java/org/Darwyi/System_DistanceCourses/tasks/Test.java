package org.Darwyi.System_DistanceCourses.tasks;

import org.Darwyi.System_DistanceCourses.Storage;
import org.Darwyi.System_DistanceCourses.usermodels.User;
import org.Darwyi.System_DistanceCourses.usermodels.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Test extends Task {
    private String testURL;

    public Test(String topic, String description, String attachment, String testURL, Double maxTestScore) {
        super(topic, description, attachment, TaskType.TEST, maxTestScore);
        this.testURL = testURL;
    }

    @Override
    public Map<Long, Double> getResults() throws Exception {
        Map <Long, Double> results = new HashMap<>();
        for (User s : Storage.Users ) {
            if (s.getRole() != UserRole.STUDENT) {
                throw new Exception("Only students have results for tasks");
            }
            if (this.isCompleted()){
                results.put(s.getId(), this.getMark());
            }
        }
        return results;
    }

    @Override
    public Double getResultsForStudent(Long id) {
        return 0.0;
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
