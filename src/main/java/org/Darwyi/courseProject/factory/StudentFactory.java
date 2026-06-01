package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Student;
import org.Darwyi.courseProject.model.User;

public class StudentFactory extends UserFactory {
    @Override protected User createUser(long id, String username, String password, String fullName){
        return new Student(id, username, password, fullName);
    }
}
