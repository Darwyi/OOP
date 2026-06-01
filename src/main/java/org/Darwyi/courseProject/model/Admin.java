package org.Darwyi.courseProject.model;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin(long id, String username, String password, String fullName){
        super(id, username, password, fullName);
    }

    @Override public UserRole getRole(){ return UserRole.ADMIN; }
    @Override public String permissions(){
        return "керування користувачами, активація курсів, перегляд статистики системи";
    }
}
