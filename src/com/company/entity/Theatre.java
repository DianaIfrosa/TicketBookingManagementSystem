package com.company.entity;

import java.util.*;

public class Theatre {
    //singleton (early initialization)
    public static final Theatre theatre = new Theatre();

    private String name = "HappyTickets"; // TODO read from file
    private String address = "Street no. 5, West";  // TODO read from file
    private Map<String, String> openHours = Map.of("Monday", "10-18", "Tuesday", "11-18");
    private List<Hall> halls; //TODO take from file
    private List<Event> futureEvents;
    private List<Event> pastEvents;

    private Theatre(){
        halls = new ArrayList<>();
        futureEvents = new ArrayList<>();
        pastEvents = new ArrayList<>();
    }

    public static Theatre getTheatre() {
        return theatre;
    }

    public List<Event> getIncomingEvents() {
        return futureEvents;
    }

    public void setIncomingEvents(List<Event> incomingEvents) {
        this.futureEvents = incomingEvents;
    }

    public void showTheatreInformation() {
        System.out.println("Welcome to " + name + "! We are happy to see you here :)");
        System.out.println("Address: " + address);
        System.out.println("Opening hours:");
        for (Map.Entry mapElement : openHours.entrySet()) {
            System.out.print(mapElement.getKey() + ": " +  mapElement.getValue() + "  ");
        }
        System.out.println();
    }

    public boolean addEvent(Event event){
        //TODO READ INFO AND ADD TO FILE HERE
        if(event != null) {
            this.futureEvents.add(event);
            return true; //done
        }
        return false; //something went wrong
    }

    public boolean deleteEvent(int id){
        //TODO REMOVE FROM FILES
        id -= 1;
        if (futureEvents == null)
            return false;
        if (futureEvents.size() == 0)
            return false;
        if (id>=0 && id<futureEvents.size()) {
            futureEvents.remove(id);
            return true; //done
        }
        else return false;
    }

    public void showFutureEvents(){
        if(futureEvents != null) {
            if(futureEvents.size() == 0)
                System.out.println("None\n");
            else
                for (int i = 0; i < futureEvents.size(); i++)
                    System.out.println((i + 1) + ": " + futureEvents.get(i));
        }
        else
            System.out.println("None\n");
    }

    public Hall[] hallsAvailable(int day, int month, int year) {
        Set<Hall> hallsSet = new HashSet<>();
        for(Hall hall: halls)
            if (hall.isAvailable())
                hallsSet.add(hall);

        for(Event event:futureEvents)
            if(event.getDay() == day && event.getMonth() == month && event.getYear() == year) //occupied hall
                hallsSet.remove(event.getHall());

        Hall[] result = new Hall[hallsSet.size()];
        hallsSet.toArray(result);

        return result;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}
