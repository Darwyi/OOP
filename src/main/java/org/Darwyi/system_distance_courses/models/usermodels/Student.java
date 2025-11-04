package org.Darwyi.system_distance_courses.models.usermodels;

import org.Darwyi.system_distance_courses.Storage;
import org.Darwyi.system_distance_courses.models.Course;
import org.Darwyi.system_distance_courses.models.Syllabus;
import org.Darwyi.system_distance_courses.models.tasks.Task;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    @Nullable
    private List<Course> ListCourses;
    @Nullable
    private Syllabus syllabus;
    @Nullable
    private List<Task> tasks;

    public Student(String firstName, String lastName, String email, String password, String bio, @Nullable Syllabus Syllabus) {
        super(firstName, lastName, email, password, bio, UserRole.STUDENT);
        this.ListCourses = new ArrayList<>();
        this.syllabus = Syllabus;
    }

    public @Nullable List<Course> getCourses() { return ListCourses; }

    public void setCourses(@Nullable List<Course> courses) {
        this.ListCourses = courses;
    }

    public @Nullable Syllabus getSyllabus() {
        return this.syllabus;
    }

    public @Nullable List<Task> getTasks() {
        return tasks;
    }

    public List<Long> getTaskIds() {
        if (this.tasks == null) {
            return null;
        }
        List<Long> taskIds = new ArrayList<>();
        for (Task t : this.tasks) {
            taskIds.add(t.getId());
        }
        return taskIds;
    }

    public void setTasks(@Nullable List<Task> tasks) {
        this.tasks = tasks;
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
    public void joinCourse(Long courseId) throws Exception {
        super.joinCourse(courseId);
        if (this.ListCourses == null) {
            throw new Exception("Student has no courses list initialized.");
        }
        ListCourses.add(Storage.getCourseById(courseId));
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
                ", Tasks=" + getTaskIds() +
                ", Role=" + getRole() +
                '}';
    }
}
