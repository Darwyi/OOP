package org.Darwyi.System_DistanceCourses.tasks;

//starts for 5 practice
public class TaskFactory {
    //public static Task createTask(String topic, String description, String attachment, Double maxMark) {
       // return new Task(topic, description, attachment, TaskType.TOPIC, maxMark);
    //}

    public static Task createTest(String topic, String description, String attachment, String testURL, Double maxMark) {
        return new Test(topic, description, attachment, testURL, maxMark);
    }

    public static Task createQuestionary(String topic, String description, String attachment, Double maxMark) {
        return new Questionary(topic, description, attachment, maxMark);
    }

    public static Task createTopic(String topic, String description, String attachment, boolean isLecture) {
        return new Topic(topic, description, attachment, isLecture);
    }

}
