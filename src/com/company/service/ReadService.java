package com.company.service;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.company.entity.*;
import com.company.user.Customer;

public class ReadService {
    // singleton (lazy initialization)
    private static ReadService readService;

    private ReadService() {}

    public static ReadService getReadService() {
        if (readService == null)
            readService = new ReadService();
        return readService;
    }

    public void readTheatre(String outputFolder) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(outputFolder + "\\theatres.csv"))) {
            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                buffer.readLine(); // skip the sep
            }
            Theatre theatre = Theatre.getTheatre();
            line = buffer.readLine(); // skip the header
            while (line != null){
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));

                if (fields.size() != 4)
                    throw new CustomException("Invalid file at path " + outputFolder + "\\theatres.csv");

                theatre.setName(fields.get(0));
                theatre.setAddress(fields.get(1));
                theatre.setDescription(fields.get(2));

                List<String> hours = Arrays.asList(fields.get(3).split(","));
                Map<String,String> mapHours = new LinkedHashMap<>();
                for(int i = 0; i < hours.size() - 1; i+=2)
                    mapHours.put(hours.get(i), hours.get(i+1));
                theatre.setOpenHours(mapHours);

                line = buffer.readLine();
            }

            List<Hall> halls = readHalls(outputFolder + "\\halls.csv");
            theatre.setHalls(halls);
            List<Event> events = readEvents(outputFolder + "\\concerts.csv", Concert.class);
            events.addAll(readEvents(outputFolder + "\\theatreplays.csv", TheatrePlay.class));

            List<List<Event>> classification = classifyEvents(events);
            // first list of events will be past ones, the second will be future ones
            theatre.setPastEvents(classification.get(0));
            theatre.setFutureEvents(classification.get(1));

            Theatre.noEvents = events.size();

        } catch (IOException|CustomException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
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

    private List<Hall> readHalls(String path) throws IOException {
        List<Hall> halls = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                 buffer.readLine(); // skip the sep
            }
            line = buffer.readLine(); // skip the header

            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));
                if (fields.size() != 5)
                    throw new CustomException("Invalid file at path" + path);

                halls.add(new Hall(fields.get(0), Integer.parseInt(fields.get(1)),
                        fields.get(2).equalsIgnoreCase("yes"), Integer.parseInt(fields.get(3)),
                        Integer.parseInt(fields.get(4))));
                line = buffer.readLine();
            }
        }
        catch (IOException|CustomException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return halls;
    }

    private  <T extends Event> List<Event> readEvents(String path, Class<T> typeClass)  throws IOException {
        List<Event> events = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                buffer.readLine(); // skip the sep
            }
            line = buffer.readLine(); // skip the header

            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));
                if (fields.size() != 12)
                    throw new CustomException("Invalid file at path " + path);

                String hallName = fields.get(1);
                Hall eventHall = null;
                // search for the event's hall
                for (Hall hall : Theatre.getTheatre().getHalls())
                    if (hall.getName().equalsIgnoreCase(hallName)) {
                        eventHall = hall;
                        break;
                    }

                if (eventHall == null)
                    throw new CustomException("Invalid file at path " + path);

                if (typeClass.toString().equalsIgnoreCase("class com.company.entity.Concert")) {
                    events.add(new Concert(Integer.parseInt(fields.get(0)), eventHall,
                            Double.parseDouble(fields.get(2)), fields.get(3), fields.get(4),
                            Integer.parseInt(fields.get(5)), Integer.parseInt(fields.get(6)),
                            Integer.parseInt(fields.get(7)), fields.get(8), fields.get(9),
                            fields.get(10), fields.get(11).equalsIgnoreCase("yes")
                    ));
                } else if (typeClass.toString().equalsIgnoreCase("class com.company.entity.TheatrePlay")) {
                    events.add(new TheatrePlay(Integer.parseInt(fields.get(0)), eventHall,
                            Double.parseDouble(fields.get(2)), fields.get(3), fields.get(4),
                            Integer.parseInt(fields.get(5)), Integer.parseInt(fields.get(6)),
                            Integer.parseInt(fields.get(7)), fields.get(8), fields.get(9),
                            fields.get(10), fields.get(11).equalsIgnoreCase("yes")
                    ));
                }

                line = buffer.readLine();
            }

        } catch (IOException | CustomException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return events;
    }

    public int checkUser(String username, String password, String path) {
        try(BufferedReader buffer = new BufferedReader(new FileReader(path))){
            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                buffer.readLine(); // skip the sep
            }
            line = buffer.readLine(); // skip the header
            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));

                if (fields.size() != 3)
                    throw new CustomException("Invalid file at path " + path);
                if (fields.get(1).equals(username) && fields.get(2).equals(password))
                    return Integer.parseInt(fields.get(0));

                line = buffer.readLine();
            }
        }
        catch (IOException | CustomException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return -1; // username and password not found
    }

    public int nextUserId(String path){
     int id = 0;
     try(BufferedReader buffer = new BufferedReader(new FileReader(path))){
        String line = buffer.readLine();
        char sep = ',';
        if (line.startsWith("sep")) {
            sep = line.charAt(line.length()-1);
            buffer.readLine(); // skip the sep
        }
        line = buffer.readLine(); // skip the header
        while (line != null) {
            id= Integer.parseInt((line.split(String.valueOf(sep)))[0]);
            line = buffer.readLine();
        }
     }
     catch (IOException e){
        System.out.println(e.getMessage());
         System.exit(1);
     }
        return id + 1;
    }

    public void readCustomerData(int customerId){
        String pathTickets = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\tickets.csv";
        String pathFavorites = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\favorites.csv";

        this.readTickets(customerId, pathTickets);
        this.readFavorites(customerId, pathFavorites);
    }

    public void readTickets(int customerId, String path){
        try(BufferedReader buffer = new BufferedReader(new FileReader(path))){

            List<Ticket> futureTickets = new ArrayList<>();
            List<Ticket> oldTickets = new ArrayList<>();

            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                buffer.readLine(); // skip the sep
            }
            line = buffer.readLine(); // skip the header
            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));

                if (fields.size() != 4)
                    throw new CustomException("Invalid file at path " + path);

                if (fields.get(0).equals(String.valueOf(customerId))) // this is the customer I'm looking for
                {
                    Event event = Theatre.getTheatre().findFutureEvent(Integer.parseInt(fields.get(1)));
                    // categorize the event: future or past
                    if (event != null) { // future event
                         // categorize the event: concert or theatre play
                        if (event instanceof Concert)
                            futureTickets.add(new ConcertTicket(fields.get(2), Double.parseDouble(fields.get(3)), event));
                        else if (event instanceof TheatrePlay)
                            futureTickets.add(new TheatrePlayTicket(fields.get(2), Double.parseDouble(fields.get(3)), event));
                    }
                    else {
                        event = Theatre.getTheatre().findPastEvent(Integer.parseInt(fields.get(1)));
                        if (event != null) // past event
                            // categorize the event: concert or theatre play
                            if (event instanceof Concert)
                                oldTickets.add(new ConcertTicket(fields.get(2), Double.parseDouble(fields.get(3)), event));
                            else if (event instanceof TheatrePlay)
                                oldTickets.add(new TheatrePlayTicket(fields.get(2), Double.parseDouble(fields.get(3)), event));
                        }
                }

                line = buffer.readLine();
            }
            Customer.getCustomer().setFutureTickets(futureTickets);
            Customer.getCustomer().setOldTickets(oldTickets);
        }
        catch (IOException | CustomException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void readFavorites(int customerId, String path){
        Set<Event> favorites = new TreeSet<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(path))){
            String line = buffer.readLine();
            char sep = ',';
            if (line.startsWith("sep")) {
                sep = line.charAt(line.length()-1);
                buffer.readLine(); // skip the sep
            }
            line = buffer.readLine(); // skip the header
            while (line != null) {
                List<String> fields = Arrays.asList(line.split(String.valueOf(sep)));

                if (fields.size() != 2)
                    throw new CustomException("Invalid file at path " + path);
                if (fields.get(0).equals(String.valueOf(customerId))) // this is the customer I'm looking for
                {
                    Event event = Theatre.getTheatre().findFutureEvent(Integer.parseInt(fields.get(1)));
                    // find the event
                    if (event != null)  // future event
                       favorites.add(event);
                    else {
                        event = Theatre.getTheatre().findPastEvent(Integer.parseInt(fields.get(1)));
                        if (event != null) // past event
                            favorites.add(event);
                    }
                }
                line = buffer.readLine();
            }
        }
        catch (IOException | CustomException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Customer.getCustomer().setFavorites(favorites);
    }
}
