package com.company.entity;

public class ConcertTicket extends Ticket {

    public ConcertTicket(String seat, double price, Event event) {
        super(seat, price, event);
    }

    public void print(){
        System.out.println(event.getNameEvent());
        System.out.println("Date and hours " + event.getDay() + "." + event.getMonth() + "." +
                          event.getYear()+ " " + event.getStartingHour() + "-" + event.getEndingHour());

        System.out.print("Seat: ");
        if(event instanceof Concert) {  // downcasting
           if(((Concert) event).getStanding())
               System.out.println("standing");
           else
               System.out.println(seat + ", price: " + price + "$");
        }
    }
}
