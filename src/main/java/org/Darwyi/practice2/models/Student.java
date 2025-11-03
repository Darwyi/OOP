package org.Darwyi.practice2.models;

import org.Darwyi.practice2.Storage;
import org.Darwyi.practice2.models.tasks.Task;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    @Nullable
    public List<Course> ListCourses;
    @Nullable
    public Syllabus syllabus;

    public Student(String firstName, String lastName, String email, String password, String bio,
                   @Nullable List<Course> course, @Nullable Syllabus Syllabus) {
        super(firstName, lastName, email, password, bio, UserRole.STUDENT);
        this.ListCourses = course;
        this.syllabus = Syllabus;
        Storage.Students.add(this);
    }

    public @Nullable List<Course> getCourses() { return ListCourses; }

    public void setCourses(@Nullable List<Course> courses) {
        this.ListCourses = courses;
    }

    public @Nullable Syllabus getSyllabus() {
        return this.syllabus;
    }

    public void markTask(boolean state, Long taskId) {
        Task task = Storage.getTaskById(taskId);
        if (Storage.Tasks.contains(task)) {
            Task newTask = Storage.getTaskById(taskId);
            Storage.Tasks.remove(task);
            newTask.setCompleted(state);
            Storage.Tasks.add(newTask);
        }
    }

    @Override
    public void AddCourse(Course course) throws Exception {
        if (this.ListCourses == null) {
            throw new Exception("Student has no courses list initialized.");
        }
        this.ListCourses.add(course);
        Storage.Courses.add(course);
    }

    public List<Long> getSyllabusCourses() {
        if (this.syllabus == null) {
            return null;
        }
        return this.syllabus.getCourses();
    }

    public List<Long> getCoursesIds() {
        if (this.ListCourses == null) {
            return null;
        }
        List<Long> CourseIDS = new ArrayList<>();
        for (Course c : this.ListCourses) {
            CourseIDS.add(c.getId());
        }

        return CourseIDS;
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
                ", Course=" + getCoursesIds() +
                ", Syllabus=" + getSyllabusCourses() +
                '}';
    }
}
