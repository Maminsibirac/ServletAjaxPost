package com.sevenbits.servlets.jdbc_test;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class JdbcTest {
    private static final Logger logger = Logger.getLogger(JdbcTest.class);

    private Connection getDBConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String password = "abrakadabra";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e) {
            logger.error("JDBC Mysql Driver NOT FOUND!");
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return connection;
    }

    public void createDBUserTable() throws SQLException{
        Connection connection = null;
        Statement statement = null;

        String createTable = "CREATE TABLE User("
                            + "NAME VARCHAR(20) NOT NULL, "
                            + "SURNAME VARCHAR(20) NOT NULL, "
                            + "EMAIL VARCHAR(20) NOT NULL, "
                            + "PRIMARY KEY (EMAIL)"
                            + ")";

        try {
            connection = getDBConnection();
            statement = connection.createStatement();

            statement.execute(createTable);
            logger.warn("Table create!");
        }
        catch (SQLException e) {}
        finally {
            if(statement != null) {
                statement.close();
            }

            if(connection != null) {
                connection.close();
            }
        }
    }

    public void insertIntoDB(Map<String, String> user) {
        Connection connection = getDBConnection();

        String insertTableValues = "INSERT INTO User"
                + "(NAME, SURNAME, EMAIL) " + "VALUES"
                + "(" + "'" + user.get("name") + "'" + "," + "'" + user.get("surname") + "'"
                + "," + "'" + user.get("mail") + "'" + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertTableValues);
            logger.info("Note is adding!");
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public Map<String, String> selectFromDB(String condition) {
        Connection connection = getDBConnection();
        String selectFromTable = "SELECT NAME, SURNAME, EMAIL FROM User WHERE " + condition;
        Map<String, String> user = new HashMap<String, String>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectFromTable);

            while (rs.next()) {
                user.put("name", rs.getString("NAME"));
                user.put("surname", rs.getString("SURNAME"));
                user.put("email", rs.getString("EMAIL"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if(user.get("name") != null) {
            logger.info("User: " + user.get("name") + " " + user.get("surname")
                    + " " + user.get("email") + "is already registered!");
        }
        else {
            logger.info("This user isn't registered");
        }

        return user;
    }
}
