package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.Main;
import ru.innopolis.stc9.connectionManager.ConnectionManager;
import ru.innopolis.stc9.connectionManager.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAOImpl implements CourseDAO {

    final static Logger logger = Logger.getLogger(Main.class);
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();

    @Override
    public Course getCourseByName(String name) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM course WHERE name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        Course course = null;
        if (resultSet.next()) {
            course = new Course(
                    resultSet.getString("name"),
                    resultSet.getInt("teacherId"),
                    resultSet.getString("description"));

        } else {
            logger.warn("Course " + name + " not found.");
        }
        connection.close();
        return course;
    }

    @Override
    public Course getCourseById(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM course WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Course course = null;
        if (resultSet.next()) {
            course = new Course(
                    resultSet.getString("name"),
                    resultSet.getInt("teacherId"),
                    resultSet.getString("description"));

        } else {
            logger.warn("Course with id " + id + " not found.");
        }
        connection.close();
        return course;
    }


    //узнать, нужна ли проверка
    @Override
    public boolean addCourse(Course course) throws SQLException {
        Connection connection = connectionManager.getConnection();
        if (getCourseByName(course.getName()) != null) {
            logger.error("Course with name " + course.getName() + " is already exists.");
            return false;
        } else {
            if (checkIfTeacherExists(course.getTeacherId())) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO course (name, teacherid, description)" +
                                "VALUES(?, ?, ?)"
                );
                statement.setString(1, course.getName());
                statement.setInt(2, course.getTeacherId());
                statement.setString(3, course.getDescription());
                boolean result = statement.execute();
                connection.close();
                logger.info("Course " + course.getName() + " successfully added.");
                return result;
            } else {
                logger.error("User id " + course.getTeacherId() + " not found or user role is not a Teacher");
                return false;
            }
        }
    }

    @Override
    public boolean updateCourse(int id, Course newCourse) throws SQLException {
        Connection connection = connectionManager.getConnection();
        if (getCourseById(id) != null) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE course SET name = ?, teacherId = ?, description = ?" +
                            "WHERE id = ?"
            );
            statement.setString(1, newCourse.getName());
            statement.setInt(2, newCourse.getTeacherId());
            statement.setString(3, newCourse.getDescription());
            statement.setInt(4, id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteCourseById(int id) throws SQLException {
        if (getCourseById(id) != null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM course WHERE id = ?"
            );
            statement.setInt(1, id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }

    private boolean checkIfTeacherExists(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement checkIfTeacherExists = connection.prepareStatement(
                "SELECT * FROM users where id = ? AND roleid = 2"
        );
        checkIfTeacherExists.setInt(1, id);
        ResultSet checkIfTeacherExistsResultSet = checkIfTeacherExists.executeQuery();
        connection.close();
        if (checkIfTeacherExistsResultSet.next()) {
            return true;
        } else {
            return false;
        }
    }
}
