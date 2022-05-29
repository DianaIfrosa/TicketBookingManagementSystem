package com.company.repository;

import com.company.database.DatabaseConfiguration;

import java.sql.*;
import java.time.Instant;

public class AuditRepository {
    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS audit" +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "action VARCHAR(150) NOT NULL," +
                "timestamp TIMESTAMP)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    static public void addToAudit(String action){
        String insertSql = "INSERT INTO audit(action, timestamp) VALUES(?, ?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            Timestamp timestamp= Timestamp.from(Instant.now());
            pstmt.setString(1, action);
            pstmt.setTimestamp(2, timestamp);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
