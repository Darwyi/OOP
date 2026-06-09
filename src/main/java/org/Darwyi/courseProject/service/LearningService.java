package org.Darwyi.courseProject.service;

import org.Darwyi.courseProject.exception.CourseNotActiveException;
import org.Darwyi.courseProject.exception.DuplicateEnrollmentException;
import org.Darwyi.courseProject.exception.EntityNotFoundException;
import org.Darwyi.courseProject.exception.ValidationException;
import org.Darwyi.courseProject.factory.AdminFactory;
import org.Darwyi.courseProject.factory.CourseContentFactory;
import org.Darwyi.courseProject.factory.StandardCourseContentFactory;
import org.Darwyi.courseProject.factory.StudentFactory;
import org.Darwyi.courseProject.factory.TeacherFactory;
import org.Darwyi.courseProject.factory.UserFactory;
import org.Darwyi.courseProject.factory.VideoCourseContentFactory;
import org.Darwyi.courseProject.model.Admin;
import org.Darwyi.courseProject.model.Certificate;
import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.model.Enrollment;
import org.Darwyi.courseProject.model.Material;
import org.Darwyi.courseProject.model.Student;
import org.Darwyi.courseProject.model.Teacher;
import org.Darwyi.courseProject.model.Test;
import org.Darwyi.courseProject.model.User;
import org.Darwyi.courseProject.observer.Event;
import org.Darwyi.courseProject.observer.EventManager;
import org.Darwyi.courseProject.observer.EventType;
import org.Darwyi.courseProject.storage.DataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LearningService {
    private final DataStore store;
    private final EventManager events;

    private final UserFactory studentFactory = new StudentFactory();
    private final UserFactory teacherFactory = new TeacherFactory();
    private final UserFactory adminFactory = new AdminFactory();

    public LearningService(DataStore store, EventManager events){
        this.store = store;
        this.events = events;
    }

    public Student registerStudent(String username, String password, String fullName){
        validateText(fullName, "ПІБ");
        Student s = (Student) studentFactory.register(store.nextUserId(), username, password, fullName);
        store.addUser(s);
        events.publish(new Event(EventType.USER_REGISTERED,
                "зареєстровано студента " + fullName, s.getId(), s.getId()));
        return s;
    }

    public Teacher registerTeacher(String username, String password, String fullName){
        validateText(fullName, "ПІБ");
        Teacher t = (Teacher) teacherFactory.register(store.nextUserId(), username, password, fullName);
        store.addUser(t);
        events.publish(new Event(EventType.USER_REGISTERED,
                "зареєстровано викладача " + fullName, t.getId(), t.getId()));
        return t;
    }

    public Admin registerAdmin(String username, String password, String fullName){
        Admin a = (Admin) adminFactory.register(store.nextUserId(), username, password, fullName);
        store.addUser(a);
        events.publish(new Event(EventType.USER_REGISTERED,
                "зареєстровано адміністратора " + fullName, a.getId(), a.getId()));
        return a;
    }

    public Course createCourse(long teacherId, String title, String description){
        validateText(title, "Назва курсу");
        Teacher t = requireTeacher(teacherId);
        Course c = new Course(store.nextCourseId(), title, description, teacherId);
        store.addCourse(c);
        t.addCourse(c.getId());
        events.publish(new Event(EventType.COURSE_CREATED, title, c.getId(), teacherId));
        return c;
    }

    public Course createCourseWithStarterContent(long teacherId, String title, String description,
                                                 CourseContentFactory factory){
        Course c = createCourse(teacherId, title, description);
        addMaterial(c.getId(), factory.createIntroLecture(store.nextMaterialId(),
                "Вступ до курсу", "Огляд курсу: " + title));
        addMaterial(c.getId(), factory.createFinalTest(store.nextMaterialId(), "Підсумковий тест"));
        return c;
    }

    public void activateCourse(long courseId){
        Course c = requireCourse(courseId);
        c.activate();
        events.publish(new Event(EventType.COURSE_ACTIVATED, c.getTitle(), c.getId(), c.getTeacherId()));
    }

    public void archiveCourse(long courseId){
        requireCourse(courseId).archive();
    }

    public Material addLecture(long courseId, String title, String body){
        return addMaterial(courseId, new StandardCourseContentFactory()
                .createIntroLecture(store.nextMaterialId(), title, body));
    }

    public Material addVideoLecture(long courseId, String title, String url){
        return addMaterial(courseId, new VideoCourseContentFactory()
                .createIntroLecture(store.nextMaterialId(), title, url));
    }

    public Test addTest(long courseId, String title, CourseContentFactory factory){
        Test test = factory.createFinalTest(store.nextMaterialId(), title);
        addMaterial(courseId, test);
        return test;
    }

    private Material addMaterial(long courseId, Material m){
        Course c = requireCourse(courseId);
        c.addMaterial(m);
        events.publish(new Event(EventType.MATERIAL_ADDED, c.getTitle(), c.getId(), c.getTeacherId()));
        return m;
    }

    public Enrollment enroll(long studentId, long courseId){
        Student s = requireStudent(studentId);
        Course c = requireCourse(courseId);
        if (!c.isActive())
            throw new CourseNotActiveException("Курс \"" + c.getTitle() + "\" не активний");
        if (store.findEnrollment(studentId, courseId) != null)
            throw new DuplicateEnrollmentException(
                    "Студент уже записаний на курс \"" + c.getTitle() + "\"");
        Enrollment e = new Enrollment(store.nextEnrollmentId(), studentId, courseId);
        store.addEnrollment(e);
        c.enroll(studentId);
        s.addCourse(courseId);
        events.publish(new Event(EventType.STUDENT_ENROLLED, c.getTitle(), c.getId(), studentId));
        return e;
    }

    public void completeLecture(long studentId, long courseId, long materialId){
        Course c = requireCourse(courseId);
        Enrollment e = requireEnrollment(studentId, courseId);
        Material m = c.findMaterial(materialId);
        if (m == null) throw new EntityNotFoundException("Матеріал не знайдено");
        if (m.isGraded())
            throw new ValidationException("Тест проходять через submitTest");
        e.markCompleted(materialId);
        events.publish(new Event(EventType.LECTURE_COMPLETED, m.getTitle(), courseId, studentId));
        checkCompletion(studentId, courseId);
    }

    public int submitTest(long studentId, long courseId, long testId, Map<Integer, Integer> answers){
        Course c = requireCourse(courseId);
        Enrollment e = requireEnrollment(studentId, courseId);
        Material m = c.findMaterial(testId);
        if (!(m instanceof Test test)) throw new EntityNotFoundException("Тест не знайдено");
        int score = test.evaluate(answers);
        e.recordScore(testId, score);
        if (test.isPassing(score)){
            e.markCompleted(testId);
            events.publish(new Event(EventType.TEST_PASSED,
                    "%s (%d%%)".formatted(test.getTitle(), score), courseId, studentId));
            checkCompletion(studentId, courseId);
        } else {
            events.publish(new Event(EventType.TEST_FAILED,
                    "%s (%d%%)".formatted(test.getTitle(), score), courseId, studentId));
        }
        return score;
    }

    private void checkCompletion(long studentId, long courseId){
        Course c = store.getCourses().get(courseId);
        Enrollment e = store.findEnrollment(studentId, courseId);
        if (c == null || e == null || e.isCertificateIssued()) return;
        int total = c.materialCount();
        if (total == 0 || e.progressPercent(total) < 100) return;

        Student s = (Student) store.getUsers().get(studentId);
        int finalScore = averageScore(e);
        Certificate cert = new Certificate(store.nextCertificateId(), studentId, courseId,
                s.getFullName(), c.getTitle(), finalScore);
        store.addCertificate(cert);
        e.setCertificateIssued(true);
        events.publish(new Event(EventType.CERTIFICATE_ISSUED,
                "сертифікат за курс \"%s\" (%d%%)".formatted(c.getTitle(), finalScore),
                courseId, studentId));
    }

    private int averageScore(Enrollment e){
        if (e.getTestScores().isEmpty()) return 100;
        int sum = 0;
        for (int v : e.getTestScores().values()) sum += v;
        return sum / e.getTestScores().size();
    }

    public void withdraw(long studentId, long courseId){
        Enrollment e = requireEnrollment(studentId, courseId);
        Course c = requireCourse(courseId);
        store.removeEnrollment(e.getId());
        c.removeStudent(studentId);
        requireStudent(studentId).removeCourse(courseId);
        events.publish(new Event(EventType.STUDENT_WITHDRAWN, c.getTitle(), c.getId(), studentId));
    }

    public void rateCourse(long studentId, long courseId, int stars){
        if (stars < 1 || stars > 5)
            throw new ValidationException("Оцінка має бути в діапазоні від 1 до 5");
        Enrollment e = requireEnrollment(studentId, courseId);
        Course c = requireCourse(courseId);
        e.setRating(stars);
        events.publish(new Event(EventType.COURSE_RATED,
                "%s (%d/5)".formatted(c.getTitle(), stars), courseId, studentId));
    }

    public double averageRating(long courseId){
        requireCourse(courseId);
        int sum = 0, n = 0;
        for (Enrollment e : store.getEnrollments().values())
            if (e.getCourseId() == courseId && e.getRating() > 0){ sum += e.getRating(); n++; }
        return n == 0 ? 0.0 : (double) sum / n;
    }

    public double completionRate(long courseId){
        requireCourse(courseId);
        int total = 0, done = 0;
        for (Enrollment e : store.getEnrollments().values())
            if (e.getCourseId() == courseId){ total++; if (e.isCertificateIssued()) done++; }
        return total == 0 ? 0.0 : done * 100.0 / total;
    }

    public List<Course> topCoursesByEnrollment(int limit){
        List<Course> all = new ArrayList<>(store.getCourses().values());
        all.sort((a, b) -> Integer.compare(b.studentCount(), a.studentCount()));
        return all.size() > limit ? new ArrayList<>(all.subList(0, limit)) : all;
    }

    public List<Course> activeCourses(){
        List<Course> res = new ArrayList<>();
        for (Course c : store.getCourses().values()) if (c.isActive()) res.add(c);
        return res;
    }

    public List<Course> coursesOfTeacher(long teacherId){
        List<Course> res = new ArrayList<>();
        for (Course c : store.getCourses().values())
            if (c.getTeacherId() == teacherId) res.add(c);
        return res;
    }

    public List<Course> coursesOfStudent(long studentId){
        List<Course> res = new ArrayList<>();
        for (Enrollment e : store.getEnrollments().values())
            if (e.getStudentId() == studentId) res.add(store.getCourses().get(e.getCourseId()));
        return res;
    }

    public List<Student> studentsOfCourse(long courseId){
        Course c = requireCourse(courseId);
        List<Student> res = new ArrayList<>();
        for (Long sid : c.getEnrolledStudentIds()){
            User u = store.getUsers().get(sid);
            if (u instanceof Student s) res.add(s);
        }
        return res;
    }

    public List<Course> searchCoursesByTitle(String query){
        String q = query == null ? "" : query.trim().toLowerCase();
        List<Course> res = new ArrayList<>();
        for (Course c : store.getCourses().values())
            if (c.getTitle().toLowerCase().contains(q)) res.add(c);
        return res;
    }

    public int progressOf(long studentId, long courseId){
        Course c = requireCourse(courseId);
        Enrollment e = requireEnrollment(studentId, courseId);
        return e.progressPercent(c.materialCount());
    }

    public List<Certificate> certificatesOf(long studentId){
        List<Certificate> res = new ArrayList<>();
        for (Certificate c : store.getCertificates().values())
            if (c.getStudentId() == studentId) res.add(c);
        return res;
    }

    private Teacher requireTeacher(long id){
        User u = store.getUsers().get(id);
        if (!(u instanceof Teacher t)) throw new EntityNotFoundException("Викладача #" + id + " не знайдено");
        return t;
    }
    private Student requireStudent(long id){
        User u = store.getUsers().get(id);
        if (!(u instanceof Student s)) throw new EntityNotFoundException("Студента #" + id + " не знайдено");
        return s;
    }
    private Course requireCourse(long id){
        Course c = store.getCourses().get(id);
        if (c == null) throw new EntityNotFoundException("Курс #" + id + " не знайдено");
        return c;
    }
    private Enrollment requireEnrollment(long studentId, long courseId){
        Enrollment e = store.findEnrollment(studentId, courseId);
        if (e == null) throw new EntityNotFoundException("Студент не записаний на курс");
        return e;
    }
    private void validateText(String value, String field){
        if (value == null || value.isBlank())
            throw new ValidationException(field + " не може бути порожнім");
    }

    public EventManager events(){ return events; }
    public DataStore store(){ return store; }
}
