package org.Darwyi.system_distance_courses;

import org.Darwyi.system_distance_courses.models.Course;
import org.Darwyi.system_distance_courses.models.Syllabus;
import org.Darwyi.system_distance_courses.models.tasks.Task;
import org.Darwyi.system_distance_courses.models.usermodels.User;

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

    public static List<Task> getTasksByUserId(Long userId) throws Exception {
        List<Task> userTasks = new ArrayList<>();
        for (User u : Users) {
            if (u.getId().equals(userId)) {
                if (u.isStudent()) {
                    userTasks.addAll(u.getTaskList());
                }
                break;
            }
        }
        return userTasks;
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
