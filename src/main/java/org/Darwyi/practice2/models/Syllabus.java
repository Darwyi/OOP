package org.Darwyi.practice2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Syllabus {
    private Long Id;
    private List<Course> Courses;
    private double size;

    public Syllabus(List<Course> Courses) {
        Id =  Math.abs(new Random().nextLong());
        this.Courses = Courses;
        this.size = Courses.size();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Course getCourse(int id) {
        return Courses.get(id);
    }

    public List<Long> getCourses() {
        List<Long> CourseIDS = new ArrayList<>();
        for (Course course : Courses) {
            CourseIDS.add(course.getId());
        }
        return CourseIDS;
    }

    public void setCourses(List<Course> courses) {
        Courses = courses;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Syllabus{" +
                "Id=" + getId() +
                ", Courses=" + getCourses() +
                ", size=" + getSize() +
                '}';
    }
}
