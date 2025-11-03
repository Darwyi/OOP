package org.Darwyi.practice2;

import org.Darwyi.practice2.models.*;
import org.Darwyi.practice2.models.tasks.Task;
import org.Darwyi.practice2.models.tasks.TaskFactory;
import org.Darwyi.practice2.models.usermodels.Student;
import org.Darwyi.practice2.models.usermodels.Teacher;
import org.Darwyi.practice2.models.usermodels.User;
import org.Darwyi.practice2.models.usermodels.UserRole;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Long> TeacherCourses = new ArrayList<>();

        User user = new User("Admin", "User", "email", "password", "bio", UserRole.ADMIN);

        Teacher teacher = new Teacher("John", "Doe", "email", "password", "BIo", TeacherCourses, null , 10);

        Course course = new Course("OOP", "Learn", teacher.getId());
        Course course1 = new Course("OOP", "Learn OOP", 0L);
        Course course2 = new Course("OOP", "Learn OOP concepts", 0L);

        Syllabus syllabus = new Syllabus(List.of(course, course1, course2));

        Student student = new Student("Jane", "Doe", "email", "password", "Bio", new ArrayList<>(), syllabus);

        Task newTask = TaskFactory.createTest("Test 1", "Description of Test 1", "new attachment", "url");
        Task newTask1 = TaskFactory.createTopic("Lecture 1", "Description of lecture 1", "new attachment", true);
        Task newTask2 = TaskFactory.createQuestionary("Questionary 1", "Description of questionary 1", "new attachment", new ArrayList<>());

        try {
            teacher.addCourse(course);
            teacher.addCourse(course1);
            teacher.addCourse(course2);
            //System.out.println(teacher.getCourses1());
            student.joinCourse(course1.getId());
            student.joinCourse(course2.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        teacher.addCourseTask(course.getId(), newTask2);


        System.out.println(user.toString());
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