package ru.innopolis.stc9;

import ru.innopolis.stc9.dao.CourseDAOImpl;
import ru.innopolis.stc9.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Course course = new Course("e", 2, "aa");
        CourseDAOImpl cdi = new CourseDAOImpl();
        cdi.addCourse(course);*/

        UserDAOImpl userDao = new UserDAOImpl();

        User user = userDao.getUser(2);
        System.out.println(user.getClass().getName());

        Student vasyaPupkin = new Student();
        vasyaPupkin.setLogin("vasyapupkin");
        vasyaPupkin.setPassword("123");
        vasyaPupkin.setFirstName("Vasya");
        vasyaPupkin.setLastName("Pupkin");

        userDao.addUser(vasyaPupkin);
    }


}
