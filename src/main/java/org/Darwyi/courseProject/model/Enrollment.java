package org.Darwyi.courseProject.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private final long studentId;
    private final long courseId;
    private final LinkedHashSet<Long> completedMaterialIds = new LinkedHashSet<>();
    private final HashMap<Long, Integer> testScores = new HashMap<>();
    private boolean certificateIssued = false;
    private int rating = 0;

    public Enrollment(long id, long studentId, long courseId){
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long getId(){ return id; }
    public long getStudentId(){ return studentId; }
    public long getCourseId(){ return courseId; }
    public Set<Long> getCompletedMaterialIds(){ return Collections.unmodifiableSet(completedMaterialIds); }
    public Map<Long, Integer> getTestScores(){ return Collections.unmodifiableMap(testScores); }
    public boolean isCertificateIssued(){ return certificateIssued; }
    public void setCertificateIssued(boolean v){ certificateIssued = v; }
    public int getRating(){ return rating; }
    public void setRating(int r){ rating = r; }

    public void markCompleted(long materialId){ completedMaterialIds.add(materialId); }
    public void recordScore(long materialId, int score){ testScores.put(materialId, score); }

    /** Відсоток проходження курсу за кількістю пройдених матеріалів. */
    public int progressPercent(int totalMaterials){
        if (totalMaterials == 0) return 0;
        return (int) Math.round(completedMaterialIds.size() * 100.0 / totalMaterials);
    }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Enrollment) o).id;
    }
    @Override public int hashCode(){ return Long.hashCode(id); }
}
