package org.Darwyi.practice2.models.usermodels;

import org.Darwyi.practice2.models.Course;
import org.Darwyi.practice2.models.Syllabus;
import org.Darwyi.practice2.models.UserRole;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.List;
import java.util.Random;

public class User {
    protected int Id;
    protected URL pfp;
    protected String FirstName;
    protected String LastName;
    private String Email;
    private String Password;
    private UserRole Role;

    public User(String FirstName, String LastName, String Email, String password, UserRole Role) {
        this.Id = new Random().nextInt();
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = password;
        this.Role = Role;
    }

    public User RegisterTeacher(
            @Nullable List<Course> course,
            @Nullable List<Syllabus> syllabus,
            @Nullable List<Student> students,
            String Bio, int Strength) {
        return new Teacher(this.FirstName, this.LastName, this.Email, this.Password, course, syllabus, students, Bio, Strength);
    }

    public User RegisterStudent(
            String Bio,
            @Nullable Course courses,
            @Nullable Syllabus syllabus){
        return new Student(this.FirstName, this.LastName, this.Email, this.Password, Bio, courses, syllabus);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public boolean isStudent() {
        return Role == UserRole.STUDENT;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + getId() +
                ", FirstName='" + getFullName() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", Password='" + getPassword() + '\'' +
                ", Role=" + getRole() +
                '}';
    }
}
