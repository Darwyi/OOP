package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Lecture;
import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Test;

public class StandardCourseContentFactory implements CourseContentFactory {
    private static final int PASS_THRESHOLD = 60;

    @Override public Material createIntroLecture(long id, String title, String content){
        return new Lecture(id, title, content);
    }
    @Override public Test createFinalTest(long id, String title){
        return new Test(id, title, PASS_THRESHOLD);
    }
    @Override public String formatName(){ return "текстовий"; }
}
