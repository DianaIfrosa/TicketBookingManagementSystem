package com.company.Tickets;

import com.company.Events.Event;

public abstract class Ticket {
    protected String seat; // e.g. A14 or C1 or null for standing event
    protected double price;
    protected Event event; // reference to Concert object or TheatrePlay object

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public abstract void print();
}
