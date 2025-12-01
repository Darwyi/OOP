package org.Darwyi.system_distance_courses.models.usermodels;

import org.Darwyi.system_distance_courses.Storage;
import org.Darwyi.system_distance_courses.models.Course;
import org.Darwyi.system_distance_courses.models.Syllabus;
import org.Darwyi.system_distance_courses.models.tasks.Task;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher extends User {
    private List<Long> ListCourses;
    @Nullable
    private List<Syllabus> Syllabus;
    private int Strength;

    public Teacher(
            String firstName, String lastName, String email, String password, String Bio,
            List<Long> Courses,
            @Nullable List<Syllabus> syllabus,
            int Strength) {
        super(firstName, lastName, email, password, Bio, UserRole.TEACHER);
        this.ListCourses = Courses;
        this.Syllabus = syllabus;
        this.Strength = Strength;
    }

    @Override
    public void addCourse(Course model) throws Exception {
        model.setTeacher(getId());
        super.Role = UserRole.TEACHER;
        Storage.Courses.add(model);
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        this.ListCourses.add(model.getId());
    }

    @Override
    public void addCourseTask(Long courseId, Task taskModel) throws Exception {
        Course courseModel = Storage.getCourseById(courseId);
        if (courseModel != null && this.ListCourses.contains(courseId)) {
            courseModel.addTask(taskModel);
        } else {
            throw new Exception("Teacher does not teach this course.");
        }
    }

    @Override
    public @Nullable List<Task> getTaskList() throws Exception {
        List<Task> teacherTasks = new ArrayList<>();
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        for (Long courseId : this.ListCourses) {
            Course course = Storage.getCourseById(courseId);
            if (course != null) {
                assert course.getListTasks() != null;
                teacherTasks.addAll(course.getListTasks());
            }
        }
        return teacherTasks;
    }

    @Override
    public void markTask(boolean state, Long taskId, Double mark, Long userId) throws Exception {
        Task task = Storage.getTaskById(taskId);
        User user = Storage.getUserById(userId);
        if (!user.isStudent()) {
            throw new Exception("User is not student.");
        }
        if (user.getTaskList().contains(task)) {
            task.setCompleted(state);
            task.setMark(mark);
        } else {
            throw new Exception("Student does not have this task assigned.");
        }
    }

    public @Nullable List<Long> getCoursesId() throws Exception {
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        List<Long> CourseIDS = new ArrayList<>();
        for(Long courseId : this.ListCourses) {
            Course course = Storage.getCourseById(courseId);
            assert course != null;
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
        for(Long courseId : this.ListCourses) {
            Course course = Storage.getCourseById(courseId);
            assert course != null;
            CourseIDS1.put(course.getId(), course.getTeacher());
        }
        return CourseIDS1;
    }

    public void setCourse(@Nullable List<Long> courses) {
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
                    ", Course=" + getCoursesId() +
                    ", Syllabus=" + getSyllabus() +
                    ", Strength=" + getStrength() +
                    ", Role=" + getRole() +
                    '}';
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
