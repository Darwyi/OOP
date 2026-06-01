package org.Darwyi.courseProject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Test extends Material {
    private static final long serialVersionUID = 1L;
    private final ArrayList<Question> questions = new ArrayList<>();
    private final int passThreshold; // поріг зарахування, %

    public Test(long id, String title, int passThreshold){
        super(id, title);
        this.passThreshold = passThreshold;
    }

    public List<Question> getQuestions(){ return Collections.unmodifiableList(questions); }
    public void addQuestion(Question q){ questions.add(q); }
    public int getPassThreshold(){ return passThreshold; }

    @Override public boolean isGraded(){ return true; }
    @Override public String typeName(){ return "Тест"; }
    @Override public String contentInfo(){
        return "%d питань, поріг %d%%".formatted(questions.size(), passThreshold);
    }

    public int evaluate(Map<Integer, Integer> answers){
        if (questions.isEmpty()) return 100;
        int correct = 0;
        for (int i = 0; i < questions.size(); i++){
            Integer ans = answers.get(i);
            if (ans != null && questions.get(i).isCorrect(ans)) correct++;
        }
        return (int) Math.round(correct * 100.0 / questions.size());
    }

    public boolean isPassing(int score){ return score >= passThreshold; }
}
