package org.Darwyi.practice2.models.tasks;

import java.util.Random;

class Question {
    private Long Id;
    private String question;
    private String rightAnswer;
    private boolean Correct;

    public Question(String question, String answer) {
        Id = Math.abs(new Random().nextLong());
        this.question = question;
        this.rightAnswer = answer;
    }

    public boolean isCorrect() {
        return Correct;
    }

    public void setCorrect(boolean correct) {
        Correct = correct;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return rightAnswer;
    }

    public void setAnswer(String answer) {
        this.rightAnswer = answer;
    }
}
