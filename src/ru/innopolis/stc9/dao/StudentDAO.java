package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.Main;
import ru.innopolis.stc9.connectionManager.ConnectionManager;
import ru.innopolis.stc9.connectionManager.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO extends UserDAOImpl {

    private int id;
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    final static Logger logger = Logger.getLogger(Main.class);
    ArrayList<Course> result = new ArrayList<>();

    public StudentDAO(int id) {
        this.id = id;
    }

    public List<Course> getCourses() throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT courseid FROM studentsatcourse WHERE studentid = ?"
        );
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
           CourseDAOImpl courseDAO = new CourseDAOImpl();
           Course course = courseDAO.getCourseById(resultSet.getInt("courseid"));
           result.add(course);
        }
        return result;
    }

    public Map<String, Integer> getGradesByCourse(int courseId) throws SQLException {
        Connection connection = connectionManager.getConnection();
        Map<String, Integer> result = new HashMap<>();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT task.description, grade.value FROM grade" +
                        "INNER JOIN task ON grade.taskid = task.id" +
                        "WHERE studentid=? AND courseid=?"
        );
        statement.setInt(1, id);
        statement.setInt(2, courseId);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
            String description = resultSet.getString("description");
            Integer value = resultSet.getInt("value");
            result.put(description, value);
        }
        return result;
    }
}
