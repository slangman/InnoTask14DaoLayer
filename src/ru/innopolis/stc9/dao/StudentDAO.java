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

    //Добавить таскам имя (поля "описание" недостаточно).
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

    public int getGradeByTask(int taskId) throws SQLException {
        int result = -1;
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT value FROM grade WHERE studentid = ? AND taskid = ?"
        );
        statement.setInt(1, id);
        statement.setInt(2, taskId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = resultSet.getInt("value");
        }
        return result;
    }

    public float getAverageGradeBuCourse(int courseId) throws SQLException {
        float result = -1;
        Map <String, Integer> grades = getGradesByCourse(courseId);
        if (grades!=null && grades.size()>0) {
            int sum=0;
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                int gradeValue = entry.getValue();
                sum += gradeValue;
            }
            result = sum/grades.size();
        }
        return result;
    }

    public boolean checkStudentOnCourse(int courseId) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM studentsatcourse WHERE courseid = ? AND studentid = ?"
        );
        statement.setInt(1, courseId);
        statement.setInt(2, id);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }
}
