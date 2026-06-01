package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Test;
import org.Darwyi.courseProject.model.VideoLecture;

public class VideoCourseContentFactory implements CourseContentFactory {
    private static final int PASS_THRESHOLD = 70;
    private static final int DEFAULT_DURATION_MIN = 15;

    @Override public Material createIntroLecture(long id, String title, String content){
        return new VideoLecture(id, title, content, DEFAULT_DURATION_MIN);
    }
    @Override public Test createFinalTest(long id, String title){
        return new Test(id, title, PASS_THRESHOLD);
    }
    @Override public String formatName(){ return "відео"; }
}
