package org.Darwyi.courseProject.storage;

import org.Darwyi.courseProject.model.Certificate;
import org.Darwyi.courseProject.model.Course;
import org.Darwyi.courseProject.model.Enrollment;
import org.Darwyi.courseProject.model.User;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

class StoreState implements Serializable {
    private static final long serialVersionUID = 1L;

    final LinkedHashMap<Long, User> users;
    final LinkedHashMap<Long, Course> courses;
    final LinkedHashMap<Long, Enrollment> enrollments;
    final LinkedHashMap<Long, Certificate> certificates;
    final long userSeq, courseSeq, materialSeq, enrollmentSeq, certificateSeq;

    StoreState(Map<Long, User> users, Map<Long, Course> courses,
               Map<Long, Enrollment> enrollments, Map<Long, Certificate> certificates,
               long userSeq, long courseSeq, long materialSeq,
               long enrollmentSeq, long certificateSeq){
        this.users = new LinkedHashMap<>(users);
        this.courses = new LinkedHashMap<>(courses);
        this.enrollments = new LinkedHashMap<>(enrollments);
        this.certificates = new LinkedHashMap<>(certificates);
        this.userSeq = userSeq;
        this.courseSeq = courseSeq;
        this.materialSeq = materialSeq;
        this.enrollmentSeq = enrollmentSeq;
        this.certificateSeq = certificateSeq;
    }
}
