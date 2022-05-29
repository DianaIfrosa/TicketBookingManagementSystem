package com.company.repository;

import com.company.database.DatabaseConfiguration;

import java.sql.*;

public class CustomerRepository {

    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS customer" +
                                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                                "username VARCHAR(20) UNIQUE NOT NULL," +
                                "password VARCHAR(30) NOT NULL)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    static public void addCustomer(String username, String password){
        String insertSql = "INSERT INTO customer(username, password) VALUES(?,?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void deleteCustomer(String username){
        String deleteSql = "DELETE FROM customer WHERE username = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try{
            PreparedStatement pstmt = connection.prepareStatement(deleteSql);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            AuditRepository.addToAudit("Customer named " + username + " deleted his/her account");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public int verifyCustomer(String username, String password){
        String selectSql = "SELECT * FROM customer WHERE username = ? AND password = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
