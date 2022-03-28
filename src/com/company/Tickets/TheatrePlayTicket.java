package com.company.Tickets;

import com.company.Events.Event;
import com.company.Events.TheatrePlay;

public class TheatrePlayTicket extends Ticket{

    public TheatrePlayTicket(String seat, double price, Event event) {
        super(seat, price, event);
    }

    public void print(){
        System.out.println(event.getNameEvent());
        System.out.println("Date and hours " + event.getDay() + "." + event.getMonth() + "." +
                event.getYear()+ " " + event.getStartingHour() + "-" + event.getEndingHour());
        System.out.println("Seat: " + seat + ", price: " + price + "$");

        if(event instanceof TheatrePlay) {  //downcasting
            System.out.println(((TheatrePlay) event).getHasIntermission());
        }
    }
}
