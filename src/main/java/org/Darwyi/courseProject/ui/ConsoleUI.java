package org.Darwyi.courseProject.ui;

import org.Darwyi.courseProject.factory.CourseContentFactory;
import org.Darwyi.courseProject.factory.StandardCourseContentFactory;
import org.Darwyi.courseProject.factory.VideoCourseContentFactory;
import org.Darwyi.courseProject.exception.LearningPlatformException;
import org.Darwyi.courseProject.model.Certificate;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private static final String STATE_FILE = "platform_state.ser";

    private final LearningService service;
    private final StatisticsCollector stats;
    private final NotificationService notifications;
    private final StateCaretaker caretaker = new StateCaretaker();
    private final Scanner in = new Scanner(System.in);

    public ConsoleUI(LearningService service, StatisticsCollector stats, NotificationService notifications){
        this.service = service;
        this.stats = stats;
        this.notifications = notifications;
    }

    public void run(){
        System.out.println("=== Система дистанційних курсів ===");
        boolean running = true;
        while (running){
            System.out.println("""

                    Головне меню:
                      1. Адміністрування
                      2. Кабінет викладача
                      3. Кабінет студента
                      4. Зберегти стан у файл
                      5. Завантажити стан із файлу
                      6. Статистика подій
                      0. Вихід""");
            switch (readInt("Вибір: ")){
                case 1 -> adminMenu();
                case 2 -> teacherMenu();
                case 3 -> studentMenu();
                case 4 -> safe(() -> {
                    caretaker.saveToFile(service.store().save(), STATE_FILE);
                    System.out.println("Стан збережено у " + STATE_FILE);
                });
                case 5 -> safe(() -> {
                    SystemMemento m = caretaker.loadFromFile(STATE_FILE);
                    service.store().restore(m);
                    System.out.println("Стан відновлено (знімок від " + m.getCreatedAt() + ")");
                });
                case 6 -> System.out.println(stats.report());
                case 0 -> running = false;
                default -> System.out.println("Невідома команда");
            }
        }
        System.out.println("Завершення роботи.");
    }

    private void adminMenu(){
        System.out.println("""
                -- Адміністрування --
                  1. Додати викладача
                  2. Додати студента
                  3. Активувати курс
                  4. Архівувати курс
                  5. Список усіх курсів
                  6. Аналітика курсів
                  0. Назад""");
        switch (readInt("Вибір: ")){
            case 1 -> safe(() -> {
                String name = readLine("ПІБ викладача: ");
                String login = readLine("Логін: ");
                System.out.println("Створено: " + service.registerTeacher(login, "pass1234", name));
            });
            case 2 -> safe(() -> {
                String name = readLine("ПІБ студента: ");
                String login = readLine("Логін: ");
                System.out.println("Створено: " + service.registerStudent(login, "pass1234", name));
            });
            case 3 -> safe(() -> {
                service.activateCourse(readLong("ID курсу: "));
                System.out.println("Курс активовано");
            });
            case 4 -> safe(() -> {
                service.archiveCourse(readLong("ID курсу: "));
                System.out.println("Курс архівовано");
            });
            case 5 -> listCourses(service.store().getCourses().values());
            case 6 -> printAnalytics();
            case 0 -> { }
            default -> System.out.println("Невідома команда");
        }
    }

    private void teacherMenu(){
        long tid = readLong("ID викладача: ");
        if (!(service.store().getUsers().get(tid) instanceof Teacher)){
            System.out.println("Викладача не знайдено"); return;
        }
        System.out.println("""
                -- Кабінет викладача --
                  1. Створити курс
                  2. Створити курс зі стартовим контентом
                  3. Додати текстову лекцію
                  4. Додати відеолекцію
                  5. Додати підсумковий тест
                  6. Мої курси
                  7. Студенти курсу
                  0. Назад""");
        switch (readInt("Вибір: ")){
            case 1 -> safe(() -> {
                String title = readLine("Назва курсу: ");
                String desc = readLine("Опис: ");
                System.out.println("Створено: " + service.createCourse(tid, title, desc));
            });
            case 2 -> safe(() -> {
                String title = readLine("Назва курсу: ");
                String desc = readLine("Опис: ");
                CourseContentFactory f = pickFactory();
                Course c = service.createCourseWithStarterContent(tid, title, desc, f);
                System.out.println("Створено курс (" + f.formatName() + " формат):");
                System.out.println("  " + c);
                for (Material m : c.getMaterials()) System.out.println("    #" + m.getId() + " " + m);
            });
            case 3 -> safe(() -> {
                long cid = readLong("ID курсу: ");
                String title = readLine("Назва лекції: ");
                String body = readLine("Текст лекції: ");
                System.out.println("Додано: " + service.addLecture(cid, title, body));
            });
            case 4 -> safe(() -> {
                long cid = readLong("ID курсу: ");
                String title = readLine("Назва відео: ");
                String url = readLine("URL: ");
                System.out.println("Додано: " + service.addVideoLecture(cid, title, url));
            });
            case 5 -> safe(() -> {
                long cid = readLong("ID курсу: ");
                String title = readLine("Назва тесту: ");
                Test test = service.addTest(cid, title, pickFactory());
                addQuestionsInteractive(test);
                System.out.println("Додано: " + test);
            });
            case 6 -> listCourses(service.coursesOfTeacher(tid));
            case 7 -> safe(() -> {
                List<Student> studs = service.studentsOfCourse(readLong("ID курсу: "));
                if (studs.isEmpty()) System.out.println("Студентів немає");
                else studs.forEach(s -> System.out.println("  " + s));
            });
            case 0 -> { }
            default -> System.out.println("Невідома команда");
        }
    }

    private CourseContentFactory pickFactory(){
        String fmt = readLine("Формат (text/video): ");
        return fmt.equalsIgnoreCase("video") ? new VideoCourseContentFactory()
                                              : new StandardCourseContentFactory();
    }

    private void addQuestionsInteractive(Test test){
        int n = readInt("Кількість питань: ");
        for (int i = 0; i < n; i++){
            String q = readLine("Питання " + (i + 1) + ": ");
            int opts = readInt("Кількість варіантів: ");
            List<String> options = new ArrayList<>();
            for (int j = 0; j < opts; j++) options.add(readLine("  Варіант " + (j + 1) + ": "));
            int correct = readInt("Номер правильного (з 1): ") - 1;
            test.addQuestion(new Question(q, options, correct));
        }
    }

    private void studentMenu(){
        long sid = readLong("ID студента: ");
        if (!(service.store().getUsers().get(sid) instanceof Student)){
            System.out.println("Студента не знайдено"); return;
        }
        System.out.println("""
                -- Кабінет студента --
                  1. Активні курси
                  2. Пошук курсу за назвою
                  3. Записатися на курс
                  4. Матеріали курсу
                  5. Завершити лекцію
                  6. Пройти тест
                  7. Мій прогрес
                  8. Мої сповіщення
                  9. Мої сертифікати
                 10. Скасувати запис на курс
                 11. Оцінити курс (1-5)
                  0. Назад""");
        switch (readInt("Вибір: ")){
            case 1 -> listCourses(service.activeCourses());
            case 2 -> listCourses(service.searchCoursesByTitle(readLine("Пошук за назвою: ")));
            case 3 -> safe(() -> {
                service.enroll(sid, readLong("ID курсу: "));
                System.out.println("Запис успішний");
            });
            case 4 -> {
                long cid = readLong("ID курсу: ");
                Course c = service.store().getCourses().get(cid);
                if (c == null) System.out.println("Курс не знайдено");
                else for (Material m : c.getMaterials())
                    System.out.println("  #" + m.getId() + " " + m);
            }
            case 5 -> safe(() -> {
                long cid = readLong("ID курсу: ");
                long mid = readLong("ID лекції: ");
                service.completeLecture(sid, cid, mid);
                System.out.println("Лекцію зараховано");
            });
            case 6 -> safe(() -> takeTest(sid));
            case 7 -> safe(() -> System.out.println(
                    "Прогрес: " + service.progressOf(sid, readLong("ID курсу: ")) + "%"));
            case 8 -> {
                List<String> inbox = notifications.inboxOf(sid);
                if (inbox.isEmpty()) System.out.println("Сповіщень немає");
                else inbox.forEach(s -> System.out.println("  • " + s));
            }
            case 9 -> {
                List<Certificate> certs = service.certificatesOf(sid);
                if (certs.isEmpty()) System.out.println("Сертифікатів немає");
                else certs.forEach(c -> System.out.println("  " + c));
            }
            case 10 -> safe(() -> {
                service.withdraw(sid, readLong("ID курсу: "));
                System.out.println("Запис на курс скасовано");
            });
            case 11 -> safe(() -> {
                long cid = readLong("ID курсу: ");
                int stars = readInt("Оцінка (1-5): ");
                service.rateCourse(sid, cid, stars);
                System.out.println("Дякуємо за оцінку!");
            });
            case 0 -> { }
            default -> System.out.println("Невідома команда");
        }
    }

    private void takeTest(long sid){
        long cid = readLong("ID курсу: ");
        long tid = readLong("ID тесту: ");
        Course c = service.store().getCourses().get(cid);
        if (c == null || !(c.findMaterial(tid) instanceof Test test)){
            System.out.println("Тест не знайдено"); return;
        }
        Map<Integer, Integer> answers = new HashMap<>();
        List<Question> qs = test.getQuestions();
        for (int i = 0; i < qs.size(); i++){
            Question q = qs.get(i);
            System.out.println((i + 1) + ". " + q.getText());
            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++)
                System.out.println("   " + (j + 1) + ") " + opts.get(j));
            answers.put(i, readInt("Ваша відповідь: ") - 1);
        }
        int score = service.submitTest(sid, cid, tid, answers);
        System.out.println("Результат: " + score + "% — " +
                (test.isPassing(score) ? "складено" : "не складено"));
    }

    private void listCourses(Collection<Course> courses){
        if (courses.isEmpty()) System.out.println("Курсів немає");
        else for (Course c : courses) System.out.println("  " + c);
    }

    private void printAnalytics(){
        Collection<Course> courses = service.store().getCourses().values();
        if (courses.isEmpty()) { System.out.println("Курсів немає"); return; }
        System.out.println("Аналітика курсів:");
        for (Course c : courses) {
            System.out.printf("  #%d %s — записів: %d, завершення: %.0f%%, рейтинг: %.1f%n",
                c.getId(), c.getTitle(), c.studentCount(),
                service.completionRate(c.getId()), service.averageRating(c.getId()));
        }
        List<Course> top = service.topCoursesByEnrollment(1);
        if (!top.isEmpty())
            System.out.println("Найпопулярніший курс: " + top.get(0).getTitle());
    }

    private int readInt(String prompt){
        System.out.print(prompt);
        while (!in.hasNextInt()){ in.next(); System.out.print("Введіть число: "); }
        int v = in.nextInt(); in.nextLine(); return v;
    }
    private long readLong(String prompt){
        System.out.print(prompt);
        while (!in.hasNextLong()){ in.next(); System.out.print("Введіть число: "); }
        long v = in.nextLong(); in.nextLine(); return v;
    }
    private String readLine(String prompt){
        System.out.print(prompt);
        return in.nextLine().trim();
    }
    private void safe(Runnable action){
        try { action.run(); }
        catch (LearningPlatformException e){ System.out.println("Помилка: " + e.getMessage()); }
    }
}
