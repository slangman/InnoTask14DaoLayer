package ru.innopolis.stc9.pojo;

public class Course {
    private String name;
    private int teacherId;
    private String description;

    public Course(String name, int teacherId, String description) {
        this.name = name;
        this.teacherId = teacherId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
