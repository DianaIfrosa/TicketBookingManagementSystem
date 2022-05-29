package com.company.repository;

import com.company.database.DatabaseConfiguration;
import com.company.entity.Concert;
import com.company.entity.Event;
import com.company.entity.TheatrePlay;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS event" +
                "(id INT PRIMARY KEY," +
                "hall_id INT NOT NULL," +
                "starting_price DOUBLE NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "description VARCHAR(100) NOT NULL," +
                "type VARCHAR(100) NOT NULL," +
                "no_available_seats INT NOT NULL," +
                "date DATE NOT NULL," +
                "starting_hour VARCHAR(7) NOT NULL," +
                "ending_hour VARCHAR(7) NOT NULL," +
                "event_type VARCHAR(15) NOT NULL," +
                "CONSTRAINT CHK_EventType CHECK (event_type = 'concert' OR event_type = 'theatre_play')," +
                "FOREIGN KEY(hall_id) REFERENCES hall(id) ON DELETE CASCADE)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean findEvent(int id){
        String selectSql = "SELECT * FROM event WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteEvent(int id){
        String selectSql = "DELETE FROM event WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addEvent(Event event){
        String insertSql = "INSERT INTO event VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String eventType = (event instanceof Concert) ? "concert":"theatre_play";

        try{
            String date_string = event.getYear() + "-" + event.getMonth() + "-" + event.getDay();
            Date date = Date.valueOf(date_string);

            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, event.getEventId());
            pstmt.setInt(2, event.getHall().getId());
            pstmt.setDouble(3, event.getStartingPrice());
            pstmt.setString(4, event.getNameEvent());
            pstmt.setString(5, event.getDescription());
            pstmt.setString(6, event.getType());
            pstmt.setInt(7, event.getNoAvailableSeats());
            pstmt.setDate(8, date);
            pstmt.setString(9, event.getStartingHour());
            pstmt.setString(10, event.getEndingHour());
            pstmt.setString(11, eventType);
            pstmt.executeUpdate();

            if (eventType.equals("concert"))
                ConcertRepository.addConcert(event.getEventId(), ((Concert)event).getStanding());

            else
                TheatrePlayRepository.addTheatrePlay(event.getEventId(), ((TheatrePlay)event).getHasIntermission());
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static String findEventType(int eventId)
    {
        String selectSql = "SELECT event_type FROM event WHERE id = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setInt(1,eventId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return rs.getString("event_type");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Event> selectEvents(){
        String selectSql = "SELECT id, event_type FROM event";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        List<Event> events = new ArrayList<>();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next()) {
                Event event;
                if(rs.getString("event_type").equals("concert"))
                    event = ConcertRepository.findConcert(rs.getInt("id"));
                else
                    event = TheatrePlayRepository.findTheatrePlay(rs.getInt("id"));
                events.add(event);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
