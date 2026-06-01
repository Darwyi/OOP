package org.Darwyi.courseProject.model;

public enum UserRole {
    STUDENT("Студент"),
    TEACHER("Викладач"),
    ADMIN("Адміністратор");

    private final String label;
    UserRole(String label){ this.label = label; }
    public String label(){ return label; }
}
