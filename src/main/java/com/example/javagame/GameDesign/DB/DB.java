package com.example.javagame.GameDesign.DB;

import java.sql.*;
import java.util.List;

public class DB {
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static DB instance = null;
    public static synchronized DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public Connection getConnection() {
        if(conn == null) {
            try {
                // Step 1: Establish a connection to the database
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root", "root", "BOluo@341924621");

                // Step 2: Create a statement object
                stmt = conn.createStatement();

                // Step 3: Execute SQL queries
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS Java");
                stmt.executeUpdate("USE Java");
                stmt.executeUpdate("DROP TABLE IF EXISTS user");
                stmt.executeUpdate("CREATE TABLE user(id INT(11) NOT NULL AUTO_INCREMENT, username VARCHAR(60) COLLATE utf8_bin NOT NULL, password VARCHAR(60) COLLATE utf8_bin NOT NULL, gender VARCHAR(60) COLLATE utf8_bin NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin");
                stmt.executeUpdate("INSERT INTO user(username, password, gender) VALUES ('123', '123', 'female')");
                System.out.println("SQL statements executed successfully.");
            } catch (SQLException ex) {
                // Handle exceptions
                ex.printStackTrace();
            }
        }
        return conn;
    }

    public int count(String countSql, List<String> vars) throws SQLException {
        preparedStatement = conn.prepareStatement(countSql);
        if (vars != null && !vars.isEmpty()) {
            for (int i = 0; i < vars.size(); i++) {
                preparedStatement.setObject(i + 1, vars.get(i));
            }
        }
        resultSet = preparedStatement.executeQuery();
        int total = 0;
        if (resultSet.next()) {
            total = resultSet.getInt(1);
        }
        return total;
    }

    public int insert(String sql, List<String> vars) throws SQLException {
        preparedStatement = null;
        resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            if (vars != null && !vars.isEmpty()) {
                for (int i = 0; i < vars.size(); i++) {
                    preparedStatement.setObject(i + 1, vars.get(i));
                }
            }
            return preparedStatement.executeUpdate();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void closeConnection() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

