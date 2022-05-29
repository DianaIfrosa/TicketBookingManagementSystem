package com.company.repository;

import com.company.database.DatabaseConfiguration;
import com.company.entity.*;
import com.company.user.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    static public void createTable(){
        String createTableSql = "CREATE TABLE IF NOT EXISTS ticket" +
                "(customer_id INT," +
                "event_id INT," +
                "seat VARCHAR(5)," +
                "price DOUBLE," +
                "FOREIGN KEY(event_id) REFERENCES event(id) ON DELETE CASCADE," +
                "PRIMARY KEY(customer_id, event_id, seat, price))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTicket(int customerId, Ticket t)
    {
        String insertSql = "INSERT INTO ticket VALUES (?,?,?,?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1,customerId);
            pstmt.setInt(2, t.getEvent().getEventId());
            pstmt.setString(3, t.getSeat());
            pstmt.setDouble(4, t.getPrice());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectCustomerTickets(int customerId) {
        String selectSql = "SELECT * FROM ticket";
        List<Ticket> futureTickets = new ArrayList<>();
        List<Ticket> oldTickets = new ArrayList<>();
        Event event;
        Ticket ticket;

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next())
            {
                // read all tickets to mark seats
                int eventId = rs.getInt("event_id");
                String event_type = EventRepository.findEventType(eventId);
                event = Theatre.getTheatre().findFutureEvent(eventId);
                if (event != null)
                    event.markSeat(rs.getString("seat"));

                // add customer's tickets into the corresponding lists
                if (rs.getInt("customer_id") == customerId) {
                    if (event_type.equals("concert"))
                    {
                        event = ConcertRepository.findConcert(eventId);
                        ticket = new ConcertTicket(rs.getString("seat"),rs.getDouble("price"), event);
                    }
                    else {
                        event = TheatrePlayRepository.findTheatrePlay(eventId);
                        ticket = new TheatrePlayTicket(rs.getString("seat"),rs.getDouble("price"), event);
                    }

                        if (Theatre.getTheatre().findPastEvent(eventId) != null) //is a past event
                            oldTickets.add(ticket);
                        else if (Theatre.getTheatre().findFutureEvent(eventId) != null) //is a future event
                            futureTickets.add(ticket);
                    }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        Customer.getCustomer().setFutureTickets(futureTickets);
        Customer.getCustomer().setOldTickets(oldTickets);
    }
}
