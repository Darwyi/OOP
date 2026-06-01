package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Test;

public interface CourseContentFactory {
    Material createIntroLecture(long id, String title, String content);
    Test createFinalTest(long id, String title);
    String formatName();
}
