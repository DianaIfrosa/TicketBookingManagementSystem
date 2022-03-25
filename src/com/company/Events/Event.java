package com.company.Events;

import com.company.Entities.Hall;

public abstract class Event {
    protected Hall hall;
    protected double startingPrice;
    protected String nameEvent, description;
    protected char[][] availableSeats; //marked with X as taken and O as free
    protected int day, month, year; //date
    protected String startingHour, endingHour;
    protected String[] feedback; // from customers

    public Event(){}

    public Event(Hall hall, double startingPrice, String nameEvent, String description, int day, int month, int year, String startingHour, String endingHour) {
        this.hall = hall;
        this.startingPrice = startingPrice;
        this.nameEvent = nameEvent;
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
    }

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

    public String[] getFeedback() {
        return feedback;
    }

    public void setFeedback(String[] feedback) {
        this.feedback = feedback;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getName() {
        return nameEvent;
    }

    public void setName(String nameEvent) {
        this.nameEvent = nameEvent;
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

    public abstract double calculatePrice(String seat);
    public abstract void presentation();

}
