package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.Main;
import ru.innopolis.stc9.connectionManager.ConnectionManager;
import ru.innopolis.stc9.connectionManager.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeDAOImpl implements GradeDAO {

    final static Logger logger = Logger.getLogger(Main.class);
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();

    @Override
    public Grade getGradeById(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM grade WHERE id = ?"
        );
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Grade grade = null;
        if (resultSet.next()) {
            grade = new Grade(
                    resultSet.getInt("taskid"),
                    resultSet.getInt("studentid"),
                    resultSet.getInt("value"));
        } else {
            logger.warn("Grade with id " + id + " not found");
        }
        connection.close();
        return grade;
    }

    @Override
    public Grade getGrade(int taskId, int studentId) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM grade WHERE taskId = ?, studentId = ?"
        );
        statement.setInt(1, taskId);
        statement.setInt(2, studentId);
        ResultSet resultSet = statement.executeQuery();
        Grade grade = null;
        if (resultSet.next()) {
            grade = new Grade(
                    resultSet.getInt("taskid"),
                    resultSet.getInt("studentid"),
                    resultSet.getInt("value"));
        } else {
            logger.warn("Grade with taskId " + taskId + " and studentId " + studentId + "not found");
        }
        connection.close();
        return grade;
    }

    @Override
    public boolean addGrade(Grade grade) throws SQLException {
        Connection connection = connectionManager.getConnection();
        if (getGrade(grade.getTaskId(), grade.getStudentId()) != null) {
            logger.error("Grade is already exists.");
            return false;
        } else {
            if (checkIfTaskExists(grade.getTaskId()) && checkIfStudentExists(grade.getStudentId())) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO grade (taskid, studentid, value)" +
                                "VALUES(?, ?, ?)"
                );
                statement.setInt(1, grade.getTaskId());
                statement.setInt(2, grade.getStudentId());
                statement.setInt(3, grade.getValue());
                boolean result = statement.execute();
                connection.close();
                logger.info("Grade added.");
                return result;
            } else {
                logger.info("Task with id " + grade.getTaskId() + " or student with id " + grade.getStudentId() + " not found.");
                return false;
            }
        }
    }

    @Override
    public boolean updateGrade(int id, Grade newGrade) throws SQLException {
        if (getGradeById(id)!=null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE grade SET taskid = ?, studentid = ?, value = ?" +
                            "WHERE id = ?"
            );
            statement.setInt(1, newGrade.getTaskId());
            statement.setInt(2, newGrade.getStudentId());
            statement.setInt(3, newGrade.getValue());
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            logger.info("Grade not found.");
            return false;
        }
    }

    @Override
    public boolean deleteGrade(int id) throws SQLException {
        if (getGradeById(id)!=null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM grade WHERE id = ?"
            );
            statement.setInt(1,id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            logger.info("Grade not found.");
            return false;
        }
    }

    private boolean checkIfStudentExists(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement checkIfStudentExists = connection.prepareStatement(
                "SELECT FROM users WHERE id = ? AND roleid = 3"
        );
        checkIfStudentExists.setInt(1, id);
        ResultSet checkIfStudentExistsRsultSet = checkIfStudentExists.executeQuery();
        connection.close();
        if (checkIfStudentExistsRsultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfTaskExists(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement checkIfTaskExists = connection.prepareStatement(
                "SELECT FROM course WHERE id = ?"
        );
        checkIfTaskExists.setInt(1, id);
        ResultSet checkIfTaskExistsResultSet = checkIfTaskExists.executeQuery();
        connection.close();
        if (checkIfTaskExistsResultSet.next()) {
            return true;
        } else {
            return false;
        }
    }
}
