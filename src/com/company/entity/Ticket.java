package com.company.entity;

public abstract class Ticket implements Comparable<Ticket>{
    protected String seat; // e.g. A14 or C1 or null for standing event
    protected double price;
    protected Event event; // reference to Concert object or TheatrePlay object

    public Ticket(){}
    public Ticket(String seat, double price, Event event) {
        this.seat = seat;
        this.price = price;
        this.event = event;
    }

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

    public int compareTo(Ticket ticket) {
        int compareResult = this.getEvent().getNameEvent().compareTo(ticket.getEvent().getNameEvent());
        if (compareResult == 0)
            return this.getSeat().compareTo(ticket.getSeat());
        else
            return compareResult;
    }
}
