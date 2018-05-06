package ru.innopolis.stc9.pojo;

public class Task {
    private String name;
    private String Description;
    private int courseId;

    public Task(String name, String description, int courseId) {
        this.name = name;
        Description = description;
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
