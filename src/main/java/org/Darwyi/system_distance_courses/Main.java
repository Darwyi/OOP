package org.Darwyi.system_distance_courses;

import org.Darwyi.system_distance_courses.models.Course;
import org.Darwyi.system_distance_courses.models.Syllabus;
import org.Darwyi.system_distance_courses.models.tasks.Task;
import org.Darwyi.system_distance_courses.models.tasks.TaskFactory;
import org.Darwyi.system_distance_courses.models.usermodels.Student;
import org.Darwyi.system_distance_courses.models.usermodels.Teacher;
import org.Darwyi.system_distance_courses.models.usermodels.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Long> TeacherCourses = new ArrayList<>();

        User teacher = new Teacher("John", "Doe", "email", "password", "BIo", TeacherCourses, null , 10);

        Course course = new Course("OOP", "Learn", teacher.getId());
        Course course1 = new Course("OOP", "Learn OOP", 0L);
        Course course2 = new Course("OOP", "Learn OOP concepts", 0L);

        Syllabus syllabus = new Syllabus(List.of(course, course1, course2));

        User student = new Student("Jane", "Doe", "email", "password", "Bio", syllabus);

        Task newTask = TaskFactory.createTest("Test 1", "Description of Test 1", "new attachment", "url", 100D);
        Task newTask1 = TaskFactory.createTopic("Lecture 1", "Description of lecture 1", "new attachment", true);
        Task newTask2 = TaskFactory.createQuestionary("Questionary 1", "Description of questionary 1", "new attachment", 100D);

        try {
            teacher.addCourse(course);
            teacher.addCourse(course1);
            teacher.addCourse(course2);
            //System.out.println(teacher.getCourses1());
            student.addCourse(course);
            student.addCourse(course1);
            student.addCourse(course2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        teacher.addCourseTask(course.getId(), newTask2);

        try {
            student.markTask(true, newTask2.getId(), 95D, student.getId());
            System.out.println(Storage.getTasksByUserId(student.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(teacher.toString());
        System.out.println(student.toString());
        System.out.println(syllabus.toString());
        System.out.println(course.toString());
        System.out.println(newTask.toString());
        System.out.println(Storage.coursesToStringIds());
        System.out.println(Storage.usersToStringIds());
        System.out.println(Storage.tasksToString());
    }
}