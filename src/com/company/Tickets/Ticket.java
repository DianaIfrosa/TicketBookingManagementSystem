package com.company.Tickets;

import com.company.Events.Event;

public abstract class Ticket {
    private String seat; // e.g. A14 or C1
    private double price;
    private Event event; // reference to Concert object or TheatrePlay object

    public abstract void print();
}
