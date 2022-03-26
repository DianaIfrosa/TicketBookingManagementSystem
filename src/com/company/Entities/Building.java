package com.company.Entities;

import com.company.Events.Event;

import java.util.*;

public class Building {
    //singleton (early initialization)
    public static final Building building = new Building();

    private String name = "CompanyName"; // TODO take from file
    private String address = "Street no. 5, West";  // TODO take from file
    private Map<String, String> openHours = Map.of("Monday", "10-18");
    private List<Hall> halls;
    private List<Event> incomingEvents, pastEvents;

    public static Building getBuilding() {
        return building;
    }

    public List<Event> getIncomingEvents() {
        return incomingEvents;
    }

    public void setIncomingEvents(List<Event> incomingEvents) {
        this.incomingEvents = incomingEvents;
    }

    public void showBuildingInformation() {
        System.out.println("Welcome to " + name + "! We are happy to see you here :)");
        System.out.println("Address: " + address);
        System.out.println("Opening hours:");
        for (Map.Entry mapElement : openHours.entrySet()) {
            System.out.print((String) mapElement.getKey() + ": " + (String) mapElement.getValue());
        }
        System.out.println();
    }

    public boolean addEvent(Event event){
        //TODO IN ADMINISTRATOR READ INFO AND ADD TO FILE HERE
        if(event != null) {
            this.incomingEvents.add(event);
            return true; //done
        }
        return false; //something went wrong
    }

    public boolean deleteEvent(int ID){
        //TODO REMOVE FROM FILES
        ID -= 1;
        if (incomingEvents == null)
            return false;
        if (incomingEvents.size() == 0)
            return false;
        if (ID>=0 && ID<incomingEvents.size()) {
            incomingEvents.remove(ID);
            return true; //done
        }
        else return false;
    }

    public void showIncomingEvents(){
        if(incomingEvents != null) {
            for (int i = 0; i < incomingEvents.size(); i++)
                System.out.println((i + 1) + ": " + incomingEvents.get(i));
        }
        else
            System.out.println("None");
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}
