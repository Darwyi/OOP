package org.Darwyi.courseProject;

import org.Darwyi.courseProject.factory.StandardCourseContentFactory;
import org.Darwyi.courseProject.factory.VideoCourseContentFactory;
import org.Darwyi.courseProject.model.Admin;
import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Question;
import org.Darwyi.courseProject.model.Student;
import org.Darwyi.courseProject.model.Teacher;
import org.Darwyi.courseProject.model.Test;
import org.Darwyi.courseProject.observer.NotificationService;
import org.Darwyi.courseProject.observer.StatisticsCollector;
import org.Darwyi.courseProject.service.LearningService;
import org.Darwyi.courseProject.storage.StateCaretaker;
import org.Darwyi.courseProject.storage.SystemMemento;

import java.util.List;
import java.util.Map;

public class Demo {
    public static void run(LearningService service, StatisticsCollector stats, NotificationService notifications){

        Admin admin = service.registerAdmin("admin", "admin123", "Адміністратор Системи");
        Teacher teacher = service.registerTeacher("soroka", "teach123", "Ігор Сорока");
        Student s1 = service.registerStudent("krasov", "stud123", "Євгеній Красовський");
        Student s2 = service.registerStudent("petrenko", "stud123", "Олена Петренко");
        System.out.println("Поліморфізм ролей:");
        System.out.println("  " + admin.getRole().label() + " — " + admin.permissions());
        System.out.println("  " + teacher.getRole().label() + " — " + teacher.permissions());
        System.out.println("  " + s1.getRole().label() + " — " + s1.permissions());

        Course course = service.createCourse(teacher.getId(),
                "Основи Java", "Вступний курс програмування на Java");
        System.out.println("\n" + course);

        Material l1 = service.addLecture(course.getId(), "Лекція 1. Синтаксис", "Типи даних, змінні, оператори.");
        Material l2 = service.addVideoLecture(course.getId(), "Лекція 2. ООП", "https://video.example/oop");
        Test test = service.addTest(course.getId(), "Підсумковий тест", new StandardCourseContentFactory());
        test.addQuestion(new Question("У що компілюється Java-код?",
                List.of("Машинний код", "Байт-код", "Асемблер"), 1));
        test.addQuestion(new Question("Який принцип ООП відповідає за приховування даних?",
                List.of("Інкапсуляція", "Поліморфізм", "Спадкування"), 0));

        service.activateCourse(course.getId());
        System.out.println("Матеріали курсу:");
        for (Material m : course.getMaterials()) System.out.println("  #" + m.getId() + " " + m);

        service.enroll(s1.getId(), course.getId());
        service.enroll(s2.getId(), course.getId());

        try { service.enroll(s1.getId(), course.getId()); }
        catch (Exception e){ System.out.println("\n[Очікуваний виняток] " + e.getMessage()); }

        service.completeLecture(s1.getId(), course.getId(), l1.getId());
        service.completeLecture(s1.getId(), course.getId(), l2.getId());
        int score = service.submitTest(s1.getId(), course.getId(), test.getId(), Map.of(0, 1, 1, 0));
        System.out.println("\nРезультат тесту (" + s1.getFullName() + "): " + score + "%");
        System.out.println("Прогрес: " + service.progressOf(s1.getId(), course.getId()) + "%");

        System.out.println("\n--- Сповіщення для " + s1.getFullName() + " ---");
        notifications.inboxOf(s1.getId()).forEach(n -> System.out.println("  • " + n));

        System.out.println("\n--- Сертифікати " + s1.getFullName() + " ---");
        service.certificatesOf(s1.getId()).forEach(c -> System.out.println("  " + c));

        System.out.println("\n" + stats.report());

        Course videoCourse = service.createCourseWithStarterContent(teacher.getId(),
                "Поглиблена Java", "Відеокурс", new VideoCourseContentFactory());
        System.out.println(videoCourse);
        for (Material m : videoCourse.getMaterials()) System.out.println("  #" + m.getId() + " " + m);

        System.out.println("\n--- Memento та серіалізація стану ---");
        StateCaretaker caretaker = new StateCaretaker();
        SystemMemento snapshot = service.store().save();
        caretaker.saveToFile(snapshot, "platform_state.ser");
        System.out.println("Збережено знімок. Курсів у системі: " + service.store().getCourses().size());

        service.createCourse(teacher.getId(), "Тимчасовий курс", "буде відкинуто при відновленні");
        System.out.println("Після додавання курсу: " + service.store().getCourses().size());

        service.store().restore(caretaker.loadFromFile("platform_state.ser"));
        System.out.println("Після відновлення зі знімка: " + service.store().getCourses().size());
        System.out.println("\n=== СЦЕНАРІЙ ЗАВЕРШЕНО ===");
    }
}
