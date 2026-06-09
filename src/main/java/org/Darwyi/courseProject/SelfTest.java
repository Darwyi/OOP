package org.Darwyi.courseProject;

import org.Darwyi.courseProject.exception.CourseNotActiveException;
import org.Darwyi.courseProject.exception.DuplicateEnrollmentException;
import org.Darwyi.courseProject.exception.EntityNotFoundException;
import org.Darwyi.courseProject.exception.ValidationException;
import org.Darwyi.courseProject.factory.StandardCourseContentFactory;
import org.Darwyi.courseProject.factory.VideoCourseContentFactory;
import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.model.Enrollment;
import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Question;
import org.Darwyi.courseProject.model.Student;
import org.Darwyi.courseProject.model.Teacher;
import org.Darwyi.courseProject.model.Test;
import org.Darwyi.courseProject.model.VideoLecture;
import org.Darwyi.courseProject.observer.EventManager;
import org.Darwyi.courseProject.observer.EventType;
import org.Darwyi.courseProject.observer.NotificationService;
import org.Darwyi.courseProject.observer.StatisticsCollector;
import org.Darwyi.courseProject.service.LearningService;
import org.Darwyi.courseProject.storage.DataStore;
import org.Darwyi.courseProject.storage.SystemMemento;

import java.util.List;
import java.util.Map;

public class SelfTest {
    private static int passed = 0, failed = 0;

    public static void run(){
        System.out.println("=== САМОТЕСТУВАННЯ ===\n");
        testUserRegistration();
        testNonTeacherCannotCreateCourse();
        testBlankTitleValidation();
        testEnrollInactiveCourse();
        testDuplicateEnrollment();
        testStarterContentFactory();
        testProgressTracking();
        testTestFailDoesNotComplete();
        testTestPassCompletes();
        testCertificateIssued();
        testStatistics();
        testNotifications();
        testSearchCourses();
        testWithdraw();
        testRateCourse();
        testAnalytics();
        testMemento();
        System.out.printf("%nПідсумок: пройдено %d з %d перевірок.%n", passed, passed + failed);
        if (failed > 0) System.out.println("УВАГА: є провалені перевірки!");
    }

    private static void check(String name, boolean cond){
        System.out.printf("  [%s] %s%n", cond ? "PASS" : "FAIL", name);
        if (cond) passed++; else failed++;
    }

    private static void expectThrows(String name, Class<? extends Throwable> type, Runnable r){
        try { r.run(); check(name, false); }
        catch (Throwable t){ check(name, type.isInstance(t)); }
    }

    private static LearningService fresh(EventManager em){
        DataStore.getInstance().clear();
        return new LearningService(DataStore.getInstance(), em);
    }

    private static void testUserRegistration(){
        LearningService s = fresh(new EventManager());
        Student st = s.registerStudent("u", "p", "Іван");
        check("Реєстрація студента отримує id=1", st.getId() == 1);
        check("Користувач збережений у сховищі", DataStore.getInstance().getUsers().get(1L) == st);
    }

    private static void testNonTeacherCannotCreateCourse(){
        LearningService s = fresh(new EventManager());
        Student st = s.registerStudent("u", "p", "Іван");
        expectThrows("Не-викладач не може створити курс (EntityNotFoundException)",
                EntityNotFoundException.class, () -> s.createCourse(st.getId(), "Курс", "опис"));
    }

    private static void testBlankTitleValidation(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викладач");
        expectThrows("Порожня назва курсу (ValidationException)",
                ValidationException.class, () -> s.createCourse(t.getId(), "   ", "опис"));
    }

    private static void testEnrollInactiveCourse(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        expectThrows("Запис на неактивний курс (CourseNotActiveException)",
                CourseNotActiveException.class, () -> s.enroll(st.getId(), c.getId()));
    }

    private static void testDuplicateEnrollment(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        expectThrows("Повторний запис (DuplicateEnrollmentException)",
                DuplicateEnrollmentException.class, () -> s.enroll(st.getId(), c.getId()));
    }

    private static void testStarterContentFactory(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Course c = s.createCourseWithStarterContent(t.getId(), "Курс", "опис",
                new VideoCourseContentFactory());
        check("Abstract Factory: створено 2 матеріали", c.materialCount() == 2);
        check("Перший продукт — відеолекція", c.getMaterials().get(0) instanceof VideoLecture);
        boolean testWith70 = c.getMaterials().get(1) instanceof Test test && test.getPassThreshold() == 70;
        check("Другий продукт — тест із порогом 70%", testWith70);
    }

    private static void testProgressTracking(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        Material l1 = s.addLecture(c.getId(), "Л1", "текст");
        s.addLecture(c.getId(), "Л2", "текст");
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        s.completeLecture(st.getId(), c.getId(), l1.getId());
        check("Прогрес 50% після 1 з 2 матеріалів", s.progressOf(st.getId(), c.getId()) == 50);
    }

    private static void testTestFailDoesNotComplete(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        Test test = s.addTest(c.getId(), "Тест", new StandardCourseContentFactory());
        test.addQuestion(new Question("Q1", List.of("A", "B"), 0));
        test.addQuestion(new Question("Q2", List.of("A", "B"), 1));
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        int score = s.submitTest(st.getId(), c.getId(), test.getId(), Map.of(0, 1, 1, 0));
        check("Невдалий тест дає 0%", score == 0);
        Enrollment e = DataStore.getInstance().findEnrollment(st.getId(), c.getId());
        check("Невдалий тест не зараховує матеріал", !e.getCompletedMaterialIds().contains(test.getId()));
    }

    private static void testTestPassCompletes(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        Test test = s.addTest(c.getId(), "Тест", new StandardCourseContentFactory());
        test.addQuestion(new Question("Q1", List.of("A", "B"), 0));
        test.addQuestion(new Question("Q2", List.of("A", "B"), 1));
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        int score = s.submitTest(st.getId(), c.getId(), test.getId(), Map.of(0, 0, 1, 1));
        check("Складений тест дає 100%", score == 100);
        Enrollment e = DataStore.getInstance().findEnrollment(st.getId(), c.getId());
        check("Складений тест зараховує матеріал", e.getCompletedMaterialIds().contains(test.getId()));
    }

    private static void testCertificateIssued(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        Material l1 = s.addLecture(c.getId(), "Л1", "текст");
        Test test = s.addTest(c.getId(), "Тест", new StandardCourseContentFactory());
        test.addQuestion(new Question("Q1", List.of("A", "B"), 0));
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        s.completeLecture(st.getId(), c.getId(), l1.getId());
        s.submitTest(st.getId(), c.getId(), test.getId(), Map.of(0, 0));
        check("Прогрес 100% після всіх матеріалів", s.progressOf(st.getId(), c.getId()) == 100);
        check("Видано рівно 1 сертифікат", s.certificatesOf(st.getId()).size() == 1);
        Enrollment e = DataStore.getInstance().findEnrollment(st.getId(), c.getId());
        check("Прапор сертифіката встановлено", e.isCertificateIssued());
    }

    private static void testStatistics(){
        EventManager em = new EventManager();
        StatisticsCollector sc = new StatisticsCollector();
        em.subscribe(sc);
        LearningService s = fresh(em);
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        s.addLecture(c.getId(), "Л1", "текст");
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        check("Усього подій = 6", sc.getTotal() == 6);
        check("USER_REGISTERED = 2", sc.countOf(EventType.USER_REGISTERED) == 2);
        check("MATERIAL_ADDED = 1", sc.countOf(EventType.MATERIAL_ADDED) == 1);
    }

    private static void testNotifications(){
        DataStore.getInstance().clear();
        EventManager em = new EventManager();
        NotificationService ns = new NotificationService(DataStore.getInstance());
        em.subscribe(ns);
        LearningService s = new LearningService(DataStore.getInstance(), em);
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        s.addLecture(c.getId(), "Нова лекція", "текст");
        check("Студент отримав сповіщення про новий матеріал", !ns.inboxOf(st.getId()).isEmpty());
    }

    private static void testSearchCourses(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        s.createCourse(t.getId(), "Основи Java", "опис");
        s.createCourse(t.getId(), "Python для початківців", "опис");
        s.createCourse(t.getId(), "Java Advanced", "опис");
        check("Пошук 'java' знаходить 2 курси", s.searchCoursesByTitle("java").size() == 2);
    }

    private static void testWithdraw(){
        EventManager em = new EventManager();
        StatisticsCollector sc = new StatisticsCollector();
        em.subscribe(sc);
        LearningService s = fresh(em);
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student st = s.registerStudent("u", "p", "Іван");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        s.activateCourse(c.getId());
        s.enroll(st.getId(), c.getId());
        s.withdraw(st.getId(), c.getId());
        check("Після скасування на курсі 0 студентів", c.studentCount() == 0);
        check("Запис видалено зі сховища",
                DataStore.getInstance().findEnrollment(st.getId(), c.getId()) == null);
        check("Подію STUDENT_WITHDRAWN зафіксовано", sc.countOf(EventType.STUDENT_WITHDRAWN) == 1);
        expectThrows("Повторне скасування (EntityNotFoundException)",
                EntityNotFoundException.class, () -> s.withdraw(st.getId(), c.getId()));
    }

    private static void testRateCourse(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student a = s.registerStudent("a", "p", "Аня");
        Student b = s.registerStudent("b", "p", "Богдан");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        s.activateCourse(c.getId());
        s.enroll(a.getId(), c.getId());
        s.enroll(b.getId(), c.getId());
        expectThrows("Некоректна оцінка 6 (ValidationException)",
                ValidationException.class, () -> s.rateCourse(a.getId(), c.getId(), 6));
        s.rateCourse(a.getId(), c.getId(), 4);
        s.rateCourse(b.getId(), c.getId(), 2);
        check("Середня оцінка курсу = 3.0", Math.abs(s.averageRating(c.getId()) - 3.0) < 1e-9);
    }

    private static void testAnalytics(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        Student a = s.registerStudent("a", "p", "Аня");
        Student b = s.registerStudent("b", "p", "Богдан");
        Course c = s.createCourse(t.getId(), "Курс", "опис");
        Material l = s.addLecture(c.getId(), "Л1", "текст");
        s.activateCourse(c.getId());
        s.enroll(a.getId(), c.getId());
        s.enroll(b.getId(), c.getId());
        s.completeLecture(a.getId(), c.getId(), l.getId());
        check("Відсоток завершення курсу = 50%", Math.abs(s.completionRate(c.getId()) - 50.0) < 1e-9);
        check("Топ-курс за популярністю — наш курс",
                s.topCoursesByEnrollment(5).get(0).getId() == c.getId());
    }

    private static void testMemento(){
        LearningService s = fresh(new EventManager());
        Teacher t = s.registerTeacher("t", "p", "Викл");
        s.createCourse(t.getId(), "Курс 1", "опис");
        SystemMemento snap = DataStore.getInstance().save();
        s.createCourse(t.getId(), "Курс 2", "опис");
        check("До відновлення — 2 курси", DataStore.getInstance().getCourses().size() == 2);
        DataStore.getInstance().restore(snap);
        check("Після відновлення зі знімка — 1 курс", DataStore.getInstance().getCourses().size() == 1);
    }
}
