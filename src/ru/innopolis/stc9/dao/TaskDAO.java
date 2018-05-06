package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Task;

import java.sql.SQLException;

public interface TaskDAO {
    public Task getTaskById(int id) throws SQLException;
    public boolean addTask(Task task) throws SQLException;
    public boolean updateTask(int id, Task newTask) throws SQLException;
    public boolean deleteTaskById(int id) throws SQLException;
}
