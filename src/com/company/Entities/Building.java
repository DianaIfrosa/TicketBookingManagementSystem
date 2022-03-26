package com.company.Entities;

import com.company.Events.Event;

import java.util.*;

public class Building {
    //singleton (early initialization)
    public static final Building building = new Building();

    private String name = "CompanyName"; // TODO read from file
    private String address = "Street no. 5, West";  // TODO read from file
    private Map<String, String> openHours = Map.of("Monday", "10-18");
    private List<Hall> halls; //TODO take from file
    private List<Event> futureEvents, pastEvents;

    private Building(){
        halls = new ArrayList<Hall>();
        futureEvents = new ArrayList<Event>();
        pastEvents = new ArrayList<Event>();
    }

    public static Building getBuilding() {
        return building;
    }

    public List<Event> getIncomingEvents() {
        return futureEvents;
    }

    public void setIncomingEvents(List<Event> incomingEvents) {
        this.futureEvents = incomingEvents;
    }

    public void showBuildingInformation() {
        System.out.println("Welcome to " + name + "! We are happy to see you here :)");
        System.out.println("Address: " + address);
        System.out.println("Opening hours:");
        for (Map.Entry mapElement : openHours.entrySet()) {
            System.out.print(mapElement.getKey() + ": " +  mapElement.getValue());
        }
        System.out.println();
    }

    public boolean addEvent(Event event){
        //TODO IN ADMINISTRATOR READ INFO AND ADD TO FILE HERE
        if(event != null) {
            this.futureEvents.add(event);
            return true; //done
        }
        return false; //something went wrong
    }

    public boolean deleteEvent(int ID){
        //TODO REMOVE FROM FILES
        ID -= 1;
        if (futureEvents == null)
            return false;
        if (futureEvents.size() == 0)
            return false;
        if (ID>=0 && ID<futureEvents.size()) {
            futureEvents.remove(ID);
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
        Set<Hall> hallsSet = new HashSet<Hall>();
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
