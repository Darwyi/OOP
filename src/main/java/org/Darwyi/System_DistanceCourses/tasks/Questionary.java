package org.Darwyi.System_DistanceCourses.tasks;

import org.Darwyi.System_DistanceCourses.Storage;
import org.Darwyi.System_DistanceCourses.usermodels.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Questionary extends Task {
    private List<Question> ListQuestions;

    public Questionary(String topic, String description, String attachment, Double maxMark) {
        super(topic, description, attachment, TaskType.QUESTION, maxMark);
        this.ListQuestions = new ArrayList<>();

    }

    public List<Question> getListQuestions() {
        return ListQuestions;
    }

    @Override
    public Map<Long, Double> getResults() throws Exception {
        Map<Long, Double> results = new HashMap<>();
        for (User user : Storage.Users) {
            if (user != null || user.isStudent()) {
                if (user.getTaskList().contains(this)) {
                    results.put(user.getId(), this.getResultsForStudent(user.getId()));
                }
            }
        }
        return results;
    }

    @Override
    public Double getResultsForStudent(Long id) throws Exception {
        User student = Storage.getUserById(id);
        if (student == null || !student.isStudent()) {
            throw new Exception("Only students have results for tasks");
        }
        List<Task> studentTasks = student.getTaskList();
        if (!studentTasks.contains(this) || !this.isCompleted()) {
            throw new Exception("Task is not completed yet or not assigned to the student");
        }
        if (studentTasks.contains(this)) {
            return this.getMark();
        }
        return null;
    }

    public List<Long> getListQuestionsIds() {
        if (ListQuestions == null) {
            return null;
        }
        List<Long> ids = new ArrayList<>();
        for (Question question : ListQuestions) {
            ids.add(question.getId());
        }
        return ids;
    }

    public void addQuestion(Question question) {
        this.ListQuestions.add(question);
    }

    public void setListQuestions(List<Question> listQuestions) {
        ListQuestions = listQuestions;
    }

    @Override
    public String toString() {
        return "Questionary{" +
                "id=" + getId() +
                ", topic='" + getTopic() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", attachment='" + getAttachment() + '\'' +
                ", ListQuestions=" + ListQuestions +
                ", type=" + getType() +
                '}';
    }

}
