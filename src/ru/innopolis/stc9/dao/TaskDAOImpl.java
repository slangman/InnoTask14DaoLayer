package ru.innopolis.stc9.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.Main;
import ru.innopolis.stc9.connectionManager.ConnectionManager;
import ru.innopolis.stc9.connectionManager.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDAOImpl implements TaskDAO {

    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    final static Logger logger = Logger.getLogger(Main.class);

    @Override
    public Task getTaskById(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM task WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Task task = null;
        if (resultSet.next()) {
            task = new Task (
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("courseId"));

        } else {
            logger.error("Task with id " + id + " not found.");
        }
        connection.close();
        return task;
    }

    public Task getTask(String name, int courseId) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM task WHERE name = ?, courseId = ?");
        statement.setString(1, name);
        statement.setInt(2, courseId);
        ResultSet resultSet = statement.executeQuery();
        Task task = null;
        if (resultSet.next()) {
            task = new Task (
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("courseId"));

        } else {
            logger.error("Task " + name + " with courseId " + courseId + "not found.");
        }
        connection.close();
        return task;
    }

    @Override
    public boolean addTask(Task task) throws SQLException {
        if (getTask(task.getName(), task.getCourseId())!=null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO task (name, description, courseId)" +
                            "VALUES(?, ?, ?)"
            );
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getCourseId());
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateTask(int id, Task newTask) throws SQLException {
        if (getTaskById(id)!=null && newTask!=null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task SET name = ?, description = ?, courseId = ?" +
                            "WHERE id = ?;"
            );
            statement.setString(1, newTask.getName());
            statement.setString(2, newTask.getDescription());
            statement.setInt(3, newTask.getCourseId());
            statement.setInt(4, id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTaskById(int id) throws SQLException {
        if (getTaskById(id) != null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM task WHERE id = ?"
            );
            statement.setInt(1, id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }
}
