package com.sevenbits.servlets.dbconnector;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.*;
import org.apache.commons.pool.impl.*;

public class DBConnector {
    private static final Logger logger = Logger.getLogger(DBConnector.class);
    GenericObjectPool connectionPool;

    public DBConnector() {
        initConnectionPool();
    }

    private void initConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String password = "abrakadabra";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connectionPool = new GenericObjectPool(null);
            ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);

            new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 1", false, true);

            new PoolingDataSource(connectionPool);
            connectionPool.setMaxIdle(20);
            connectionPool.setMaxActive(100);
        }
        catch (ClassNotFoundException e) {
            logger.error("JDBC Mysql Driver NOT FOUND!");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public final Connection getDBConnection() {
        try {
            if(connectionPool.getMaxActive() <= connectionPool.getNumActive()) {
                logger.warn("Connection limit is over!!!");
            }

            Connection connection = (Connection)connectionPool.borrowObject();

            return connection;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public final void returnDBConnection(Connection connection) {
        if(connection == null) {
            logger.error("Returning NULL to pool");
            return;
        }

        try {
            connectionPool.returnObject(connection);
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void createDBUserTable() {
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
        catch (SQLException e) {
            logger.error(e.getMessage());
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException e) {
                    logger.error(e.getMessage());
                }
            }

            returnDBConnection(connection);
        }
    }

    public void insertIntoDB(Map<String, String> user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getDBConnection();
            statement = connection.prepareStatement("INSERT INTO User "
                    + "(NAME, SURNAME, EMAIL) VALUES(?, ?, ?)");

            statement.setString(1, user.get("name"));
            statement.setString(2, user.get("surname"));
            statement.setString(3, user.get("mail"));
            statement.executeUpdate();

            logger.info("User: " + user.get("name") + " " + user.get("surname")
                    + " " + user.get("mail") + " is adding!");
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException e) {
                    logger.error(e.getMessage());
                }
            }

            returnDBConnection(connection);
        }
    }

    public Map<String, String> selectFromDB(String condition) {
        Connection connection = null;
        PreparedStatement statement = null;
        Map<String, String> user = new HashMap<String, String>();

        try {
            connection = getDBConnection();
            statement = connection.prepareStatement("SELECT NAME, SURNAME, EMAIL FROM User WHERE EMAIL=?");
            statement.setString(1, condition);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                user.put("name", rs.getString("NAME"));
                user.put("surname", rs.getString("SURNAME"));
                user.put("email", rs.getString("EMAIL"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        finally {
            returnDBConnection(connection);
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        if(user.get("name") != null) {
            logger.info("User: " + user.get("name") + " " + user.get("surname")
                    + " " + user.get("email") + "is already registered!");
        }

        return user;
    }
}
