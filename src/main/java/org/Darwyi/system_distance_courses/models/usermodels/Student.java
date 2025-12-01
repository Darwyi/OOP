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
        this.tasks = new ArrayList<>();
    }

    public @Nullable List<Course> getCourses() { return ListCourses; }

    public void setCourses(@Nullable List<Course> courses) {
        this.ListCourses = courses;
    }

    public @Nullable Syllabus getSyllabus() {
        return this.syllabus;
    }

    @Override
    public @Nullable List<Task> getTaskList() {
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

    @Override
    public void markTask(boolean state, Long taskId, Double mark, Long userId) throws Exception {
        Task task = Storage.getTaskById(taskId);
        if (task != null && tasks.contains(task)) {
            Task newTask = Storage.getTaskById(taskId);
            tasks.remove(task);
            Storage.Tasks.remove(task);
            newTask.setCompleted(state);
            tasks.add(newTask);
            Storage.Tasks.add(newTask);
        }
    }

    @Override
    public void addCourse(Course model) throws Exception {
        Course choosenCourse = Storage.getCourseById(model.getId());
        if (choosenCourse != null) {
            this.Role = UserRole.STUDENT;
            choosenCourse.addToStudentsList(getId());
        }
        if (this.ListCourses == null) {
            throw new Exception("Student has no courses list initialized.");
        }
        ListCourses.add(Storage.getCourseById(model.getId()));
    }

    @Override
    public void addCourseTask(Long courseId, Task taskModel) throws Exception {
        throw new Exception("Students cannot add tasks to courses.");
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
