package org.Darwyi.courseProject.model;

import java.io.Serializable;

public abstract class Material implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final long id;
    protected String title;

    protected Material(long id, String title){
        this.id = id;
        this.title = title;
    }

    public long getId(){ return id; }
    public String getTitle(){ return title; }

    public abstract String typeName();

    public abstract String contentInfo();

    public boolean isGraded(){ return false; }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Material) o).id;
    }

    @Override
    public int hashCode(){ return Long.hashCode(id); }

    @Override
    public String toString(){
        return "[%s] %s — %s".formatted(typeName(), title, contentInfo());
    }
}
