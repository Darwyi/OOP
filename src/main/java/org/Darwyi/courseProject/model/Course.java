package org.Darwyi.courseProject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private String title;
    private String description;
    private final long teacherId;
    private CourseStatus status = CourseStatus.DRAFT;
    private final ArrayList<Material> materials = new ArrayList<>();
    private final LinkedHashSet<Long> enrolledStudentIds = new LinkedHashSet<>();

    public Course(long id, String title, String description, long teacherId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
    }

    public long getId(){ return id; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public long getTeacherId(){ return teacherId; }
    public CourseStatus getStatus(){ return status; }

    public List<Material> getMaterials(){ return Collections.unmodifiableList(materials); }
    public Set<Long> getEnrolledStudentIds(){ return Collections.unmodifiableSet(enrolledStudentIds); }
    public int materialCount(){ return materials.size(); }
    public int studentCount(){ return enrolledStudentIds.size(); }

    public void activate(){ status = CourseStatus.ACTIVE; }
    public void archive(){ status = CourseStatus.ARCHIVED; }
    public boolean isActive(){ return status == CourseStatus.ACTIVE; }

    public void addMaterial(Material m){ materials.add(m); }
    public void enroll(long studentId){ enrolledStudentIds.add(studentId); }

    public Material findMaterial(long materialId){
        for (Material m : materials) if (m.getId() == materialId) return m;
        return null;
    }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Course) o).id;
    }
    @Override public int hashCode(){ return Long.hashCode(id); }

    @Override public String toString(){
        return "Курс #%d \"%s\" [%s], матеріалів: %d, студентів: %d"
                .formatted(id, title, status.label(), materials.size(), enrolledStudentIds.size());
    }
}
