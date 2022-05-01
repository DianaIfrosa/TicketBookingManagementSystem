package com.company.Events;

import com.company.Entities.Hall;

public abstract class Event {
    protected Hall hall;
    protected double startingPrice;
    protected String nameEvent, description, type;
    protected char[][] availableSeats; //marked with X as taken and O as free
    protected int noAvailableSeats;
    protected int day, month, year;
    protected String startingHour, endingHour;

    public Event(){}

    public Event(Hall hall, double startingPrice, String nameEvent, String description, String type, int day, int month, int year, String startingHour, String endingHour) {
        this.hall = hall;
        this.startingPrice = startingPrice;
        this.nameEvent = nameEvent;
        this.type = type;
        this.description = description;

        this.availableSeats = new char [hall.getRows()][hall.getColumns()];
        for(int i=0; i<hall.getRows(); i++)
            for(int j=0; j<hall.getColumns(); j++)
                availableSeats[i][j] = 'O'; //available sign

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
                System.out.print(availableSeats[i][j] + " ");
            System.out.println();
        }
    }

    public void markSeat(int i, int j) {
        availableSeats[i][j] = 'X';
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char[][] getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(char[][] availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString(){
        return nameEvent + " " + this.day + "-" + this.month + "-" + this.year;
    }

    public abstract double calculatePrice(String seat);
    public abstract void presentation();

}

