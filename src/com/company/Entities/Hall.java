package com.company.Entities;

import java.util.*;

public class Hall {
    private String name; //identifier
    private int number; //identifier
    private int floor;
    private int seatsNumber;
    private boolean available;
    private HashMap<Character, Integer> seats;

    public Hall(String name, int number, int floor, int seatsNumber, boolean available,HashMap<Character, Integer> seats) {
        this.name = name;
        this.number = number;
        this.floor = floor;
        this.seatsNumber = seatsNumber;
        this.available = available;
        this.seats = seats;
    }

    public String toString() {
        return "Hall name: " + name + "\nHall number: " + number + "\n Floor: " + floor;
    }
}
