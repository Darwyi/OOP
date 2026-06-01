package org.Darwyi.courseProject.factory;

import org.Darwyi.courseProject.model.User;

public abstract class UserFactory {

    public final User register(long id, String username, String password, String fullName){
        return createUser(id, username, password, fullName);
    }

    protected abstract User createUser(long id, String username, String password, String fullName);
}
