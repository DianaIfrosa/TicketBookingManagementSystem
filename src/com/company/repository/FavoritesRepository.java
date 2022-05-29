package com.company.repository;

import com.company.database.DatabaseConfiguration;
import com.company.entity.Event;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FavoritesRepository {

    static public void createTable(){

        String createAssociativeTableSql = "CREATE TABLE IF NOT EXISTS favorites"+
                "(id_customer INT," +
                "id_show INT," +
                "PRIMARY KEY (id_customer, id_show)," +
                "FOREIGN KEY (id_customer) REFERENCES customer(id) ON DELETE CASCADE," +
                "FOREIGN KEY (id_show) REFERENCES event(id) ON DELETE CASCADE)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            stmt.execute(createAssociativeTableSql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    static public void addFavoriteEvent(int customerId, int eventId){
        String insertSql = "INSERT INTO favorites VALUES(?,?)";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void deleteFavoriteEvent(int idCustomer, int idEvent){
        String insertSql = "DELETE FROM favorites WHERE id_customer = ? AND id_show = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try{
            PreparedStatement pstmt = connection.prepareStatement(insertSql);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, idEvent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void showUserFavoriteEvents(int customerId) {
        String selectSQL = "SELECT event.id Id, event.name Name  FROM favorites " +
                            "JOIN customer ON favorites.id_customer = customer.id " +
                            "JOIN event ON event.id = favorites.id_show " +
                            "WHERE customer.id = ?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                System.out.println(rs.getInt("Id") + ". " + rs.getString("Name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  Set<Event> selectCustomerFavorites(int customerId){
        String selectSQL = "SELECT id_show FROM favorites WHERE id_customer = ?";
        Set<Event> favs = new HashSet<>();
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
               Event event =  ConcertRepository.findConcert(rs.getInt("id_show"));
               if (event == null)
                   event = TheatrePlayRepository.findTheatrePlay(rs.getInt("id_show"));
               favs.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favs;
    }
}