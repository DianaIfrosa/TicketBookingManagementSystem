package com.company.repository;

import com.company.database.DatabaseConfiguration;
import com.company.entity.Hall;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallRepository {
    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS hall" +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "floor INT NOT NULL," +
                "available BOOL DEFAULT True," +
                "rows_no INT NOT NULL," +
                "columns_no INT NOT NULL)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Hall findHall(int hallId){

        String selectSql = "SELECT * FROM hall WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        Hall hall = null;
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, hallId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                hall = new Hall(rs.getInt("id"), rs.getString("name"), rs.getInt("floor"),
                        rs.getBoolean("available"), rs.getInt("rows_no"), rs.getInt("columns_no"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
      return hall;
    }

    public static List<Hall> selectHalls(){
        List<Hall> halls = new ArrayList<>();
        String selectSql = "SELECT * FROM hall";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next())
            {
                Hall hall = new Hall(rs.getInt("id"), rs.getString("name"), rs.getInt("floor"),
                        rs.getBoolean("available"), rs.getInt("rows_no"), rs.getInt("columns_no"));
                halls.add(hall);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }

    public static void addHall(Hall hall){
        String insertSql = "INSERT INTO hall(name, floor, available, rows_no, columns_no) VALUES (?,?,?,?,?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, hall.getName());
            pstmt.setInt(2, hall.getFloor());
            pstmt.setBoolean(3, hall.isAvailable());
            pstmt.setInt(4, hall.getRows());
            pstmt.setInt(5, hall.getColumns());

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showHalls(){
        String selectSql = "SELECT * FROM hall";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        Hall hall = null;
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next())
            {
                hall = new Hall(rs.getInt("id"), rs.getString("name"), rs.getInt("floor"),
                        rs.getBoolean("available"), rs.getInt("rows_no"), rs.getInt("columns_no"));
                hall.showDetails();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteHall(int hallId){
        String selectSql = "DELETE FROM hall WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, hallId);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
