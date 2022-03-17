package com.company.Entities;

import java.util.*;

public class Building {
    //singleton (early initialization)
    public static final Building building = new Building();

    private String name;
    private String address;
    private HashMap<String, String> openHours;
    private List<Hall> halls;

    private Building() {
        //TODO READ
        openHours = new HashMap<String, String>(7);
        //Monday: 10-16 etc.
        /*
        this.address = address;
        this.openHours = openHours;
        this.halls = halls;
        */
    }

    public static Building getBuilding() {
        return building;
    }

    public void showBuildingInformation() {
        System.out.println("Welcome to " + name);
        System.out.println("Address: " + address);
        for (Map.Entry mapElement : openHours.entrySet()) {
            System.out.println((String)mapElement.getKey() + ": " + (String)mapElement.getValue());
        }
    }
}
