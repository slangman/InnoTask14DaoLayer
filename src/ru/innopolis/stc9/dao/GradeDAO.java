package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Grade;
import ru.innopolis.stc9.pojo.Task;

import java.sql.SQLException;

public interface GradeDAO {
    public Grade getGradeById(int id) throws SQLException;

    public Grade getGrade(int taskId, int studentId) throws SQLException;

    public boolean addGrade(Grade grade) throws SQLException;

    public boolean updateGrade(int id, Grade newGrade) throws SQLException;

    public boolean deleteGrade(int id) throws SQLException;
}
