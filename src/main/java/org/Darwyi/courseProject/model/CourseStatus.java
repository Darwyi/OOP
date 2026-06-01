package org.Darwyi.courseProject.model;

public enum CourseStatus {
    DRAFT("Чернетка"),
    ACTIVE("Активний"),
    ARCHIVED("Архівований");

    private final String label;
    CourseStatus(String label){ this.label = label; }
    public String label(){ return label; }
}
