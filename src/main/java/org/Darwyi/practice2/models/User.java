package org.Darwyi.practice2.models;

import org.Darwyi.practice2.Storage;

import java.net.URL;
import java.util.Random;

public class User {
    protected Long Id;
    protected URL pfp;
    protected String FirstName;
    protected String LastName;
    private String Email;
    private String Password;
    private String Bio;
    private UserRole Role;

    public User(String FirstName, String LastName, String Email, String password, String bio, UserRole Role) {
        this.Id =  Math.abs(new Random().nextLong());
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = password;
        this.Bio = bio;
        this.Role = Role;
        Storage.Users.add(this);
    }

    public void AddCourse(Course course) throws Exception {
        System.out.println("AddCourse(), User");
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public URL getPfp() {
        return pfp;
    }

    public void setPfp(URL pfp) {
        this.pfp = pfp;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFullName() {
        return String.join(" ", FirstName, LastName);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        Role = role;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public boolean isStudent() {
        return Role == UserRole.STUDENT;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + getId() +
                ", FullName='" + getFullName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", Password='" + getPassword() + '\'' +
                ", Bio='" + getBio() + '\'' +
                ", Role=" + getRole() +
                '}';
    }
}
