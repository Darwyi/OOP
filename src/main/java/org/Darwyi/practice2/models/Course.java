package org.Darwyi.practice2.models;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class Course {
    private int Id;
    private String Name;
    private String Description;
    private String Category;
    private String Level;
    private String Language;
    private double Price;
    @Nullable
    private String Teacher;

    public Course(String Name, String Description, String Category,
                  String Level, String Language, double Price, @Nullable String teacher) {
        this.Id = new Random().nextInt();
        this.Name = Name;
        this.Description = Description;
        this.Category = Category;
        this.Level = Level;
        this.Language = Language;
        this.Price = Price;
        this.Teacher = teacher;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Description='" + getDescription() + '\'' +
                ", Category='" + getCategory() + '\'' +
                ", Level='" + getLevel() + '\'' +
                ", Language='" + getLanguage() + '\'' +
                ", Price=" + getPrice() +
                ", Teacher='" + getTeacher() + '\'' +
                '}';
    }
}
