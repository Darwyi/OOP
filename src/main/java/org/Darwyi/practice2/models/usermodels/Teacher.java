package org.Darwyi.practice2.models.usermodels;

import org.Darwyi.practice2.models.Course;
import org.Darwyi.practice2.models.Syllabus;
import org.Darwyi.practice2.models.UserRole;
import org.jetbrains.annotations.Nullable;

import java.util.List;

class Teacher extends User {
    @Nullable
    private List<Course> Course;
    @Nullable
    private List<Syllabus> Syllabus;
    @Nullable
    private List<Student> Students;
    private int Strength;
    private String Bio;

    public Teacher(
            String firstName, String lastName, String email, String password,
            @Nullable List<Course> Courses,
            @Nullable List<Syllabus> syllabus,
            @Nullable List<Student> students,
            String Bio, int Strength) {
        super(firstName, lastName, email, password, UserRole.TEACHER);
        this.Course = Courses;
        this.Syllabus = syllabus;
        this.Students = students;
        this.Strength = Strength;
        this.Bio = Bio;
    }

    public Course CreateCourse(String Name, String Description,
                               String Category, String Level,
                               String Language, double Price) {
        Course course = new Course(Name, Description, Category, Level, Language, Price, getFullName());
        this.Course.add(course);

        return course;
    }

    public @Nullable List<Course> getCourse() {
        return Course;
    }

    public void setCourse(@Nullable List<Course> course) {
        Course = course;
    }

    public @Nullable List<Syllabus> getSyllabus() {
        return Syllabus;
    }

    public void setSyllabus(@Nullable List<Syllabus> syllabus) {
        Syllabus = syllabus;
    }

    public @Nullable List<Student> getStudents() {
        return Students;
    }

    public void setStudents(@Nullable List<Student> students) {
        Students = students;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int strength) {
        Strength = strength;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Id=" + getId() +
                ", Pfp=" + getPfp() +
                ", FullName='" + getFullName() + '\'' +
                ", Course=" + getCourse() +
                ", Syllabus=" + getSyllabus() +
                ", Students=" + getStudents() +
                ", Strength=" + getStrength() +
                ", Bio='" + getBio() + '\'' +
                '}';
    }

}
