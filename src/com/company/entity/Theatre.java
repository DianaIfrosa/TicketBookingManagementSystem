package com.company.entity;

import java.util.*;

public class Theatre {
    // singleton (early initialization)
    public static final Theatre theatre = new Theatre();

    private final String name = "Happy Tickets";
    private final String address = "Street no. 5A, New York";
    private final String description = "Our job is to bring you exciting and sophisticated shows, so that you can relax and create amazing memories!";
    private final Map<String, String> openHours = Map.of("Monday", "10-18",
            "Tuesday", "10-18",
            "Wednesday", "10-18",
            "Thursday", "10-18",
            "Friday", "10-16",
            "Saturday", "10-14",
            "Sunday","closed");
    private List<Hall> halls;
    private List<Event> futureEvents;
    private List<Event> pastEvents;

    public static int noEvents = 0;

    private Theatre(){
        halls = new ArrayList<>();
        futureEvents = new ArrayList<>();
        pastEvents = new ArrayList<>();
    }

    public static Theatre getTheatre() {
        return theatre;
    }

    public void showTheatreInformation() {
        System.out.println("Welcome to " + name + "! We are happy to see you here :)");
        System.out.println(description);
        System.out.println("Address: " + address);
        System.out.println("Opening hours:");
        for (Map.Entry<String, String> mapElement : openHours.entrySet()) {
            System.out.print(mapElement.getKey() + ": " +  mapElement.getValue() + "  ");
        }
        System.out.println();
    }

    public boolean addEvent(Event event){
        if(event != null) {
            this.futureEvents.add(event);
            return true; // done
        }
        return false; // something went wrong
    }

    public boolean deleteEvent(int id){
        if (futureEvents == null)
            return false;
        if (futureEvents.size() == 0)
            return false;

        boolean found = false;

        for (int i = 0; i < futureEvents.size(); i++)
            if (futureEvents.get(i).getEventId() == id) {
                found = true;
                futureEvents.remove(i);
                break;
            }

        return found;
    }

    public void showFutureEvents(){
        if(futureEvents != null) {
            if(futureEvents.size() == 0)
                System.out.println("None\n");
            else
                for (Event event : futureEvents)
                    System.out.println(event.getEventId() + ": " + event);
        }
        else
            System.out.println("None\n");
    }

    public void showPastEvents(){
        if(pastEvents != null) {
            if(pastEvents.size() == 0)
                System.out.println("None\n");
            else
                for (Event event : pastEvents)
                    System.out.println(event.getEventId() + ": " + event);
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
            if(event.getDay() == day && event.getMonth() == month && event.getYear() == year) // occupied hall
                hallsSet.remove(event.getHall());

        Hall[] result = new Hall[hallsSet.size()];
        hallsSet.toArray(result);

        return result;
    }

    public Event findFutureEvent(int id){
        for(Event event:futureEvents)
            if (event.getEventId() == id)
                return event;
        return null;
    }

    public Event findPastEvent(int id){
        for(Event event:pastEvents)
            if (event.getEventId() == id)
                return event;
        return null;
    }

    public void addHall(Hall hall){
        halls.add(hall);
    }

    public void deleteHall(int id){

        int index = -1 ;
        for(int i=0; i<halls.size(); i++)
           if (halls.get(i).getId() == id)
               index = i;
        if(index != -1)
            halls.remove(index);
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public List<Event> getFutureEvents() {
        return futureEvents;
    }

    public List<Event> getPastEvents() {
        return pastEvents;
    }
    public void setFutureEvents(List<Event> futureEvents) {
        this.futureEvents = futureEvents;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public void setPastEvents(List<Event> pastEvents) {
        this.pastEvents = pastEvents;
    }
}
