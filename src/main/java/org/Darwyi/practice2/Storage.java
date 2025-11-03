package org.Darwyi.practice2;

import org.Darwyi.practice2.models.*;
import org.Darwyi.practice2.models.tasks.Task;
import org.Darwyi.practice2.models.usermodels.Student;
import org.Darwyi.practice2.models.usermodels.Teacher;
import org.Darwyi.practice2.models.usermodels.User;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static List<Course> Courses = new ArrayList<>();
    public static List<Task> Tasks = new ArrayList<>();
    public static List<User> Users = new ArrayList<>();
    public static List<Syllabus> Syllabuses = new ArrayList<>();

    public static Task getTaskById(Long id) {
        for (Task t : Tasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static User getUserById(Long id) {
        for (User s : Users) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static Course getCourseById(Long id) {
        for (Course c : Courses) {
            if (c.getId().equals(id)) {
                return c;
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
    public static String usersToStringIds() {
        List<Long> userIds = new ArrayList<>();
        for (User u : Users) {
            userIds.add(u.getId());
        }
        return userIds.toString();
    }

    public static String tasksToString() {
        List<Long> taskIds = new ArrayList<>();
        for (Task t : Tasks) {
            taskIds.add(t.getId());
        }
        return taskIds.toString();
    }
}
