package org.Darwyi.courseProject.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    private final LinkedHashSet<Long> enrolledCourseIds = new LinkedHashSet<>();

    public Student(long id, String username, String password, String fullName){
        super(id, username, password, fullName);
    }

    @Override public UserRole getRole(){ return UserRole.STUDENT; }
    @Override public String permissions(){
        return "перегляд активних курсів, запис на курс, проходження матеріалів і тестів";
    }

    public Set<Long> getEnrolledCourseIds(){ return Collections.unmodifiableSet(enrolledCourseIds); }
    public void addCourse(long courseId){ enrolledCourseIds.add(courseId); }
    public void removeCourse(long courseId){ enrolledCourseIds.remove(courseId); }
}
