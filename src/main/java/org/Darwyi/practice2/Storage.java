package org.Darwyi.practice2;

import org.Darwyi.practice2.models.*;
import org.Darwyi.practice2.models.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static List<Course> Courses = new ArrayList<>();
    public static List<Task> Tasks = new ArrayList<>();
    public static List<User> Users = new ArrayList<>();
    public static List<Teacher> Teachers = new ArrayList<>();
    public static List<Student> Students = new ArrayList<>();
    public static List<Syllabus> Syllabuses = new ArrayList<>();

    public static Task getTaskById(Long id) {
        for (Task t : Tasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static Student getStudentById(Long id) {
        for (Student s : Students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static String coursesToStringIds() {
        List<Long> courseIds = new ArrayList<>();
        for (Course c : Courses) {
            courseIds.add(c.getId());
        }
        return courseIds.toString();
    }
    public static String teachersToStringIds() {
        List<Long> teachersIds = new ArrayList<>();
        for (Teacher t : Teachers) {
            teachersIds.add(t.getId());
        }
        return teachersIds.toString();
    }
    public static String studentsToStringIds() {
        List<Long> studentsIds = new ArrayList<>();
        for (Student s : Students) {
            studentsIds.add(s.getId());
        }
        return studentsIds.toString();
    }

}
