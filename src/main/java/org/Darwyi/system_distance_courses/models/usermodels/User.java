package org.Darwyi.system_distance_courses.models.usermodels;

import org.Darwyi.system_distance_courses.Storage;
import org.Darwyi.system_distance_courses.models.Course;
import org.Darwyi.system_distance_courses.models.tasks.Task;

import java.net.URL;
import java.util.List;
import java.util.Random;

public abstract class User {
    protected Long Id;
    protected URL pfp;
    protected String FirstName;
    protected String LastName;
    private String Email;
    private String Password;
    private String Bio;
    protected UserRole Role;

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

    public abstract void addCourse(Course model) throws Exception;

    public abstract void addCourseTask(Long courseId, Task taskModel) throws Exception;

    public abstract void markTask(boolean state, Long taskId, Double mark, Long userId) throws Exception;

    public abstract List<Task> getTaskList() throws Exception;

    public static User getUserById(Long userId) {
        return Storage.getUserById(userId);
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
