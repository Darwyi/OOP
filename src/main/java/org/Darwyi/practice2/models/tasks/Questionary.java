package org.Darwyi.practice2.models.tasks;

import java.util.ArrayList;
import java.util.List;

class Questionary extends Task {
    private List<Question> ListQuestions;

    public Questionary(String topic, String description, String attachment, List<Question> listQuestions) {
        super(topic, description, attachment, TaskType.QUESTION);
        this.ListQuestions = listQuestions;

    }

    public List<Question> getListQuestions() {
        return ListQuestions;
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
