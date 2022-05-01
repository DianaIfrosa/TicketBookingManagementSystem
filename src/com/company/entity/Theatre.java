package com.company.entity;

import java.util.*;

public class Theatre {
    // singleton (early initialization)
    public static final Theatre theatre = new Theatre();

    private String name;
    private String address;
    private String description;
    private Map<String, String> openHours;
    private List<Hall> halls;
    private List<Event> futureEvents;
    private List<Event> pastEvents;

    public static int noEvents = 0;

    private Theatre(){
        halls = new ArrayList<>();
        futureEvents = new ArrayList<>();
        pastEvents = new ArrayList<>();
        openHours = new LinkedHashMap<>();
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

    public boolean deleteEvent(Event event){
        if (futureEvents == null)
            return false;
        if (futureEvents.size() == 0)
            return false;

        boolean found = false;

        for (int i = 0; i < futureEvents.size(); i++)
            if (futureEvents.get(i).getEventId() == event.getEventId()) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpenHours(Map<String, String> openHours) {
        this.openHours = openHours;
    }

    public void setPastEvents(List<Event> pastEvents) {
        this.pastEvents = pastEvents;
    }
}
