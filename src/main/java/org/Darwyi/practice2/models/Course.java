package org.Darwyi.practice2.models;

import org.Darwyi.practice2.models.tasks.Task;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class Course {
    private Long Id;
    private String Name;
    private String Description;
    private Long Teacher;
    @Nullable
    private List<Task> ListTasks;

    public Course(String Name, String Description, Long teacher, @Nullable List<Task> listTasks) {
        this.Id = Math.abs(new Random().nextLong());
        this.Name = Name;
        this.Description = Description;
        this.Teacher = teacher;
        this.ListTasks = listTasks;
    }

    public List<Task> getListTasks() {
        return ListTasks;
    }

    public void setListTasks(List<Task> listTasks) {
        ListTasks = listTasks;
    }

    public List<Long> getListTasksIds() {
        if (ListTasks == null) {
            return null;
        }
        List<Long> taskIds = new java.util.ArrayList<>();
        for (Task t : ListTasks) {
            taskIds.add(t.getId());
        }
        return taskIds;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Long getTeacher() {
        return Teacher;
    }
    public void setTeacher(Long teacher) {
        Teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Description='" + getDescription() + '\'' +
                ", Teacher='" + getTeacher() + '\'' +
                ", ListTasks=" + getListTasksIds() +
                '}';
    }
}
