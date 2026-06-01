package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.Admin;
import org.Darwyi.courseProject.model.User;

public class AdminFactory extends UserFactory {
    @Override protected User createUser(long id, String username, String password, String fullName){
        return new Admin(id, username, password, fullName);
    }
}
