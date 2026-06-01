package org.Darwyi.courseProject.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final long id;
    protected String username;
    protected String password;
    protected String fullName;

    protected User(long id, String username, String password, String fullName){
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public long getId(){ return id; }
    public String getUsername(){ return username; }
    public String getFullName(){ return fullName; }
    public void setFullName(String fullName){ this.fullName = fullName; }
    public boolean checkPassword(String pw){ return password != null && password.equals(pw); }

    public abstract UserRole getRole();

    public abstract String permissions();

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((User) o).id;
    }

    @Override
    public int hashCode(){ return Long.hashCode(id); }

    @Override
    public String toString(){
        return "%s #%d %s (%s)".formatted(getRole().label(), id, fullName, username);
    }
}
