package com.company.database;

import com.company.entity.Event;
import com.company.entity.Hall;
import com.company.entity.Theatre;
import com.company.repository.*;
import com.company.user.Customer;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatabaseManager {
    // singleton
    private static DatabaseManager databaseManager;

    private DatabaseManager(){ initDatabase();}
    public static DatabaseManager getDatabaseManager() {
        if (databaseManager == null)
            databaseManager = new DatabaseManager();
        return databaseManager;
    }

    public static int lastIdFromTable(String tableName)
    {
        String selectSql = "SELECT MAX(id) FROM " + tableName;
        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<List<Event>> classifyEvents(List<Event> events) {
        // get current day, month and year
        int currDay, currMonth, currYear;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar obj = Calendar.getInstance();
        List<String> date =  Arrays.asList(formatter.format(obj.getTime()).split("/"));
        currDay = Integer.parseInt(date.get(0));
        currMonth = Integer.parseInt(date.get(1));
        currYear= Integer.parseInt(date.get(2));

        List<Event> futureEvents =  new ArrayList<>();
        List<Event> pastEvents =  new ArrayList<>();
        List<List<Event>> classification = new ArrayList<>();

        for (Event event : events) {
            int day = event.getDay();
            int month = event.getMonth();
            int year = event.getYear();

            if (year < currYear)
                pastEvents.add(event);
            else if (year > currYear)
                futureEvents.add(event);
            else{
                if (month < currMonth)
                    pastEvents.add(event);
                else if (month > currMonth)
                    futureEvents.add(event);
                else
                {
                    if (day < currDay)
                        pastEvents.add(event);
                    else
                        futureEvents.add(event);
                }
            }
        }
        classification.add(pastEvents);
        classification.add(futureEvents);

        return classification;
    }

    private void initDatabase() {
        AdministratorRepository.createTable();
        CustomerRepository.createTable();
        AuditRepository.createTable();

        HallRepository.createTable();
        EventRepository.createTable();
        ConcertRepository.createTable();
        TheatrePlayRepository.createTable();

        FavoritesRepository.createTable();
        TicketRepository.createTable();
    }
    public void initTheatre(){
        Theatre theatre = Theatre.getTheatre();

        List<Hall> halls = HallRepository.selectHalls();
        theatre.setHalls(halls);

        List<Event> events = EventRepository.selectEvents();
        List<List<Event>> classification = classifyEvents(events);
        // first list of events will be past ones, the second will be future ones
        theatre.setPastEvents(classification.get(0));
        theatre.setFutureEvents(classification.get(1));
        Theatre.noEvents = events.size();
    }

    public void initCustomer(){
        Set<Event> favs = FavoritesRepository.selectCustomerFavorites(Customer.getCustomer().getId());
        Customer.getCustomer().setFavorites(favs);

        TicketRepository.selectCustomerTickets(Customer.getCustomer().getId());
    }
}
