package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Course;

import java.sql.SQLException;

public interface CourseDAO {
    public Course getCourseByName(String name) throws SQLException;
    public Course getCourseById(int id) throws SQLException;
    public boolean addCourse (Course course) throws SQLException;
    public boolean updateCourse(int id, Course newCourse) throws SQLException;
    public boolean deleteCourseById (int id) throws SQLException;
}
