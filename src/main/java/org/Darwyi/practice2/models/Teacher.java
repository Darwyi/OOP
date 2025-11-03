package org.Darwyi.practice2.models;

import org.Darwyi.practice2.Storage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher extends User {
    @Nullable
    private List<Course> ListCourses;
    @Nullable
    private List<Syllabus> Syllabus;
    private int Strength;

    public Teacher(
            String firstName, String lastName, String email, String password, String Bio,
            @Nullable List<Course> Courses,
            @Nullable List<Syllabus> syllabus,
            int Strength) {
        super(firstName, lastName, email, password, Bio, UserRole.TEACHER);
        this.ListCourses = Courses;
        this.Syllabus = syllabus;
        this.Strength = Strength;
        Storage.Teachers.add(this);
    }
    @Override
    public void AddCourse(Course course) throws Exception {
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        this.ListCourses.add(course);
        Storage.Courses.add(course);
    }

    public @Nullable List<Long> getCourses() throws Exception {
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        List<Long> CourseIDS = new ArrayList<>();
        for(Course course : this.ListCourses) {
            CourseIDS.add(course.getId());
        }
        return CourseIDS;
    }
    //debug func
    public @Nullable Map<Long,Long> getCourses1() throws Exception {
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        Map<Long, Long> CourseIDS1 = new HashMap<>();
        for(Course course : this.ListCourses) {
            CourseIDS1.put(course.getId(), course.getTeacher());
        }
        return CourseIDS1;
    }

    public void setCourse(@Nullable List<Course> courses) {
        ListCourses = courses;
    }

    public @Nullable List<Syllabus> getSyllabus() {
        return Syllabus;
    }

    public void setSyllabus(@Nullable List<Syllabus> syllabus) {
        Syllabus = syllabus;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    @Override
    public String toString() {
        try {
            return "Teacher{" +
                    "Id=" + getId() +
                    ", Pfp=" + getPfp() +
                    ", FullName='" + getFullName() + '\'' +
                    ", Course=" + getCourses() +
                    ", Syllabus=" + getSyllabus() +
                    ", Strength=" + getStrength() +
                    '}';
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
