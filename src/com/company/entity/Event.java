package com.company.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Event implements Comparable<Event>{
    protected int eventId;
    protected Hall hall;
    protected double startingPrice;
    protected String nameEvent;
    protected String description;
    protected String type;
    protected List<List<Character>> availableSeats; // marked with X as taken and O as free
    protected int noAvailableSeats;
    protected int day;
    protected int month;
    protected int year;
    protected String startingHour;
    protected String endingHour;


    public Event(){}

    public Event(int eventId, Hall hall, double startingPrice, String nameEvent, String description, String type, int day, int month, int year, String startingHour, String endingHour) {
        this.eventId = eventId;
        this.hall = hall;
        this.startingPrice = startingPrice;
        this.nameEvent = nameEvent;
        this.type = type;
        this.description = description;

        this.availableSeats = new ArrayList<>();
        for(int i=0; i<hall.getRows(); i++) {
            List<Character> aux = new ArrayList<>();
            for (int j = 0; j < hall.getColumns(); j++)
                aux.add('O'); // available sign
            availableSeats.add(aux);
        }

        this.day = day;
        this.month = month;
        this.year = year;
        this.startingHour = startingHour;
        this.endingHour = endingHour;

        this.noAvailableSeats = hall.getSeatsNumber();
    }

    public void showAvailableSeats() {
        System.out.print("  ");
        for(int i=0 ; i<hall.getColumns(); i++)
            System.out.print((char)('A' + i) + " ");
        System.out.println();
        for(int i=0 ; i<hall.getRows(); i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < hall.getColumns(); j++)
                System.out.print(availableSeats.get(i).get(j) + " ");
            System.out.println();
        }
    }

    public void markSeat(String seat) {
        char letter = seat.charAt(0);
        int number = Integer.parseInt(seat.substring(1)) - 1;
        List<Character> line = availableSeats.get(number);
        line.set(letter-'A', 'X');
        availableSeats.set(number, line);
    }

    public abstract double calculatePrice(String seat);

    public abstract void presentation();

    @Override
    public int compareTo(Event e) {
        // sort ascending based on event name
        return this.getNameEvent().compareTo(e.getNameEvent());
    }

    public int getNoAvailableSeats() { return noAvailableSeats; }

    public void setNoAvailableSeats(int noAvailableSeats) { this.noAvailableSeats = noAvailableSeats; }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public List<List<Character>> getAvailableSeats() {
        return availableSeats;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String toString(){
        return nameEvent + " " + this.day + "-" + this.month + "-" + this.year;
    }
}


