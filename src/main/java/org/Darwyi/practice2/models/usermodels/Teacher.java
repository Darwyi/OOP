package org.Darwyi.practice2.models.usermodels;

import org.Darwyi.practice2.Storage;
import org.Darwyi.practice2.models.Course;
import org.Darwyi.practice2.models.Syllabus;
import org.Darwyi.practice2.models.tasks.Task;
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
        super.addCourse(model);
        if (this.ListCourses == null) {
            throw new Exception("Teacher has no courses list initialized.");
        }
        this.ListCourses.add(model.getId());
    }

    public void addCourseTask(Long courseId, Task taskModel) throws Exception {
        Course courseModel = Storage.getCourseById(courseId);
        if (courseModel != null && this.ListCourses.contains(courseId)) {
            courseModel.addTask(taskModel);
        } else {
            throw new Exception("Teacher does not teach this course.");
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
