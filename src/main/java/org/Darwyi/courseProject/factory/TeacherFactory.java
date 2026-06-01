package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Teacher;
import org.Darwyi.courseProject.model.User;

public class TeacherFactory extends UserFactory {
    @Override protected User createUser(long id, String username, String password, String fullName){
        return new Teacher(id, username, password, fullName);
    }
}
