package org.Darwyi.practice2.models.tasks;

import java.util.List;

//starts for 5 practice
public class TaskFactory {
    public static Task createTask(String topic, String description, String attachment) {
        return new Task(topic, description, attachment, TaskType.TOPIC);
    }

    public static Task createTest(String topic, String description, String attachment, String testURL) {
        return new Test(topic, description, attachment, testURL);
    }

    public static Task createQuestionary(String topic, String description, String attachment, List<Question> listQuestions) {
        return new Questionary(topic, description, attachment, listQuestions);
    }

    public static Task createTopic(String topic, String description, String attachment, boolean isLecture) {
        return new Topic(topic, description, attachment, isLecture);
    }

}
