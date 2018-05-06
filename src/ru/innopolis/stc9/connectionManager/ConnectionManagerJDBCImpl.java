package ru.innopolis.stc9.connectionManager;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerJDBCImpl implements ConnectionManager {

    final static Logger logger = Logger.getLogger(Main.class);
    private static ConnectionManager connectionManager;

    public static ConnectionManager getInstance(){
        if (connectionManager == null){
            connectionManager = new ConnectionManagerJDBCImpl();
        }
        return connectionManager;
    }

    private ConnectionManagerJDBCImpl(){

    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/academicPerformance",
                    "postgres",
                    "365308");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

}
