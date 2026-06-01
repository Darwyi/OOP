package org.Darwyi.courseProject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;
    private final ArrayList<String> options;
    private final int correctIndex;

    public Question(String text, List<String> options, int correctIndex){
        this.text = text;
        this.options = new ArrayList<>(options);
        this.correctIndex = correctIndex;
    }

    public String getText(){ return text; }
    /** Незмінне представлення списку варіантів відповіді. */
    public List<String> getOptions(){ return Collections.unmodifiableList(options); }
    public boolean isCorrect(int index){ return index == correctIndex; }
}
