package org.Darwyi.courseProject.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Teacher extends User {
    private static final long serialVersionUID = 1L;
    private final LinkedHashSet<Long> courseIds = new LinkedHashSet<>();

    public Teacher(long id, String username, String password, String fullName){
        super(id, username, password, fullName);
    }

    @Override public UserRole getRole(){ return UserRole.TEACHER; }
    @Override public String permissions(){
        return "створення курсів, додавання матеріалів, перегляд студентів своїх курсів";
    }

    public Set<Long> getCourseIds(){ return Collections.unmodifiableSet(courseIds); }
    public void addCourse(long courseId){ courseIds.add(courseId); }
}
