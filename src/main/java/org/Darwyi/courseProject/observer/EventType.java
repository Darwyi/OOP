package org.Darwyi.courseProject.observer;

public enum EventType {
    USER_REGISTERED("Реєстрація користувача"),
    COURSE_CREATED("Створення курсу"),
    COURSE_ACTIVATED("Активація курсу"),
    MATERIAL_ADDED("Додавання матеріалу"),
    STUDENT_ENROLLED("Запис студента на курс"),
    LECTURE_COMPLETED("Завершення лекції"),
    TEST_PASSED("Тест складено"),
    TEST_FAILED("Тест не складено"),
    CERTIFICATE_ISSUED("Видача сертифіката"),
    STUDENT_WITHDRAWN("Скасування запису"),
    COURSE_RATED("Оцінювання курсу");

    private final String label;
    EventType(String label){ this.label = label; }
    public String label(){ return label; }
}
