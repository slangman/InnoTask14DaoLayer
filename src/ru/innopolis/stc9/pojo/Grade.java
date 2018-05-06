package ru.innopolis.stc9.pojo;

public class Grade {
    private int taskId;
    private int studentId;
    private int value;

    public Grade(int taskId, int studentId, int value) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.value = value;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
