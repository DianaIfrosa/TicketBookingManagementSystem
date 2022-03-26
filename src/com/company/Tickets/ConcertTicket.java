package com.company.Tickets;

import com.company.Events.Concert;
import com.company.Events.Event;

public class ConcertTicket extends Ticket{

    public void print(){
        System.out.println(event.getNameEvent());
        System.out.println("Date and hours " + event.getDay() + "." + event.getMonth() + "." +
                          event.getYear()+ " " + event.getStartingHour() + "-" + event.getEndingHour());

        System.out.print("Seat: ");
        if(event instanceof Concert) {  //downcasting
           if(((Concert) event).isStanding())
               System.out.println("standing");
           else
               System.out.println(seat + ", price: " + price + "$");
        }
    }
}
