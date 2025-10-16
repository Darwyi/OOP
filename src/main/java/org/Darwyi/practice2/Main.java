package org.Darwyi.practice2;

import org.Darwyi.practice2.models.Course;
import org.Darwyi.practice2.models.Syllabus;
import org.Darwyi.practice2.models.UserRole;
import org.Darwyi.practice2.models.*;
import org.Darwyi.practice2.models.usermodels.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User tUser = new User("John", "Doe", "email", "password", UserRole.TEACHER);
        User teacher = tUser.RegisterTeacher(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "nio", 5);

        Course course = new Course("OOP", "Learn OOP concepts", "Programming1",
                "Beginner", "English", 49.99, teacher.getFullName());
        Course course1 = new Course("OOP", "Learn OOP", "Programming",
                "Middle", "English", 100, null);
        Course course2 = new Course("OOP", "Learn OOP concepts", "Programming",
                "Senior", "English", 150, null);

        Syllabus syllabus = new Syllabus(List.of(course, course1, course2));

        User sUser = new User("Jane", "Doe", "email", "password", UserRole.STUDENT);
        User student = sUser.RegisterStudent("Bio", course1, syllabus);


        System.out.println(tUser.toString());
        System.out.println(teacher.toString());
        System.out.println(sUser.toString());
        System.out.println(student.toString());
        System.out.println(syllabus.toString());
        System.out.println(course.toString());
    }
}