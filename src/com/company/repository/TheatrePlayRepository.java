package com.company.repository;

import com.company.database.DatabaseConfiguration;
import com.company.entity.Hall;
import com.company.entity.TheatrePlay;

import java.sql.*;
import java.util.Calendar;

public class TheatrePlayRepository {
    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS theatre_play" +
                "(id INT PRIMARY KEY," +
                "hasIntermission BOOL NOT NULL," +
                "FOREIGN KEY(id) REFERENCES event(id) ON DELETE CASCADE)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    static public void addTheatrePlay(int id, boolean hasIntermission) {
        String insertSql = "INSERT INTO theatre_play VALUES (?,?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, id);
            pstmt.setBoolean(2, hasIntermission);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static TheatrePlay findTheatrePlay(int eventId) {
        String selectSql = "SELECT * FROM theatre_play t JOIN event e ON t.id=e.id WHERE t.id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        TheatrePlay event = null;
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Date date = rs.getDate("date");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Hall hall = HallRepository.findHall(rs.getInt("hall_id"));

                event = new TheatrePlay(rs.getInt("id"), hall, rs.getDouble("starting_price"),
                        rs.getString("name"), rs.getString("description"), day, month, year,
                        rs.getString("starting_hour"), rs.getString("ending_hour"), rs.getString("type"),
                        rs.getBoolean("hasIntermission"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    return event;
    }
}
