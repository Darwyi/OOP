package org.Darwyi.courseProject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Certificate implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private final long studentId;
    private final long courseId;
    private final String studentName;
    private final String courseTitle;
    private final int finalScore;
    private final LocalDateTime issuedAt;

    public Certificate(long id, long studentId, long courseId,
                       String studentName, String courseTitle, int finalScore){
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentName = studentName;
        this.courseTitle = courseTitle;
        this.finalScore = finalScore;
        this.issuedAt = LocalDateTime.now();
    }

    public long getId(){ return id; }
    public long getStudentId(){ return studentId; }
    public long getCourseId(){ return courseId; }
    public int getFinalScore(){ return finalScore; }
    public LocalDateTime getIssuedAt(){ return issuedAt; }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Certificate) o).id;
    }
    @Override public int hashCode(){ return Long.hashCode(id); }

    @Override public String toString(){
        return "Сертифікат #%d: %s — курс \"%s\", результат %d%%, видано %s"
                .formatted(id, studentName, courseTitle, finalScore, issuedAt.toLocalDate());
    }
}
