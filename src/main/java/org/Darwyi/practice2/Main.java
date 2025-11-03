package org.Darwyi.practice2;

import org.Darwyi.practice2.models.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Course> TeacherCourses = new ArrayList<>();

        User user = new User("Admin", "User", "email", "password", "bio", UserRole.ADMIN);

        Teacher teacher = new Teacher("John", "Doe", "email", "password", "BIo", TeacherCourses, null , 10);

        Course course = new Course("OOP", "Learn", teacher.getId(), null);
        Course course1 = new Course("OOP", "Learn OOP", 0L, null);
        Course course2 = new Course("OOP", "Learn OOP concepts", 0L, null);

        Syllabus syllabus = new Syllabus(List.of(course, course1, course2));

        Student student = new Student("Jane", "Doe", "email", "password", "Bio", new ArrayList<>(), syllabus);

        try {
            teacher.AddCourse(course);
            teacher.AddCourse(course1);
            teacher.AddCourse(course2);
            //System.out.println(teacher.getCourses1());
            student.AddCourse(course1);
            student.AddCourse(course2);
            student.AddCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(user.toString());
        System.out.println(teacher.toString());
        System.out.println(student.toString());
        System.out.println(syllabus.toString());
        System.out.println(course.toString());
        System.out.println(Storage.coursesToStringIds());
        System.out.println(Storage.teachersToStringIds());
        System.out.println(Storage.studentsToStringIds());
    }
}