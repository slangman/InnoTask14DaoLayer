package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public interface UserDAO {
    public User getUser(int id) throws SQLException;
    public boolean addUser(User user) throws SQLException;
    public boolean updateUser(int id, User newUser) throws SQLException;
    public boolean deleteUser(int id) throws SQLException;
}
