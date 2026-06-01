package org.Darwyi.courseProject.storage;

import org.Darwyi.courseProject.model.Certificate;
import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.model.Enrollment;
import org.Darwyi.courseProject.model.User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DataStore {
    private static DataStore instance;

    private Map<Long, User> users = new LinkedHashMap<>();
    private Map<Long, Course> courses = new LinkedHashMap<>();
    private Map<Long, Enrollment> enrollments = new LinkedHashMap<>();
    private Map<Long, Certificate> certificates = new LinkedHashMap<>();

    private long userSeq = 0, courseSeq = 0, materialSeq = 0,
                 enrollmentSeq = 0, certificateSeq = 0;

    private DataStore(){}

    public static synchronized DataStore getInstance(){
        if (instance == null) instance = new DataStore();
        return instance;
    }

    public long nextUserId(){ return ++userSeq; }
    public long nextCourseId(){ return ++courseSeq; }
    public long nextMaterialId(){ return ++materialSeq; }
    public long nextEnrollmentId(){ return ++enrollmentSeq; }
    public long nextCertificateId(){ return ++certificateSeq; }

    public Map<Long, User> getUsers(){ return Collections.unmodifiableMap(users); }
    public Map<Long, Course> getCourses(){ return Collections.unmodifiableMap(courses); }
    public Map<Long, Enrollment> getEnrollments(){ return Collections.unmodifiableMap(enrollments); }
    public Map<Long, Certificate> getCertificates(){ return Collections.unmodifiableMap(certificates); }

    public void addUser(User u){ users.put(u.getId(), u); }
    public void addCourse(Course c){ courses.put(c.getId(), c); }
    public void addEnrollment(Enrollment e){ enrollments.put(e.getId(), e); }
    public void addCertificate(Certificate c){ certificates.put(c.getId(), c); }

    public Enrollment findEnrollment(long studentId, long courseId){
        for (Enrollment e : enrollments.values())
            if (e.getStudentId() == studentId && e.getCourseId() == courseId) return e;
        return null;
    }

    public SystemMemento save(){
        StoreState snapshot = new StoreState(users, courses, enrollments, certificates,
                userSeq, courseSeq, materialSeq, enrollmentSeq, certificateSeq);
        return new SystemMemento(SerializationUtil.deepCopy(snapshot));
    }

    public void restore(SystemMemento memento){
        StoreState s = SerializationUtil.deepCopy(memento.getState());
        this.users = s.users;
        this.courses = s.courses;
        this.enrollments = s.enrollments;
        this.certificates = s.certificates;
        this.userSeq = s.userSeq;
        this.courseSeq = s.courseSeq;
        this.materialSeq = s.materialSeq;
        this.enrollmentSeq = s.enrollmentSeq;
        this.certificateSeq = s.certificateSeq;
    }

    public void clear(){
        users = new LinkedHashMap<>();
        courses = new LinkedHashMap<>();
        enrollments = new LinkedHashMap<>();
        certificates = new LinkedHashMap<>();
        userSeq = courseSeq = materialSeq = enrollmentSeq = certificateSeq = 0;
    }
}
