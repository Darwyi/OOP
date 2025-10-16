package org.Darwyi.practice2.models.usermodels;

import org.Darwyi.practice2.models.Course;
import org.Darwyi.practice2.models.Syllabus;
import org.Darwyi.practice2.models.UserRole;
import org.jetbrains.annotations.Nullable;

class Student extends User {
    public String Bio;
    @Nullable
    public Course course;
    @Nullable
    public Syllabus syllabus;

    public Student(String firstName, String lastName, String email, String password,
                   String Bio, @Nullable Course course, @Nullable Syllabus Syllabus) {
        super(firstName, lastName, email, password, UserRole.STUDENT);
        this.Bio = Bio;
        this.course = course;
        this.syllabus = Syllabus;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public @Nullable Course getCourse() { return course; }

    public void setCourses(@Nullable Course courses) {
        this.course = courses;
    }

    public @Nullable Syllabus getSyllabus() {
        return this.syllabus;
    }

    public void setSyllabus(@Nullable Syllabus syllabus) {
        this.syllabus = syllabus;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + getId() +
                ", Pfp=" + getPfp() +
                ", FullName='" + getFullName() + '\'' +
                ", Bio='" + getBio() + '\'' +
                ", Course=" + getCourse().getId() +
                ", Syllabus=" + Syllabus.getId() +
                '}';
    }
}
