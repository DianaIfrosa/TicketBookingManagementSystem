package com.company.Users;

import com.company.Entities.Building;
import com.company.Events.Event;
import com.company.Tickets.Ticket;

import java.util.List;

public class Customer {
    //singleton (lazy initialization) because it refers to current user
    public static Customer customer;
    private List<String> favorites;
    private List<Ticket> futureTickets, oldTickets; //when a show is over the corresponding ticket goes to oldTickets section

    private Customer(){}
    public static Customer getCustomer() {
        if (customer == null)
            customer = new Customer();

        return customer;
    }

    public void seeIncomingEvents() {
        Building building = Building.getBuilding();
        System.out.println("Incoming events:");
        building.showIncomingEvents();
    }

    public void searchUsingDate(int day, int month, int year) {
        Building building = Building.getBuilding();
        List<Event> events = building.getIncomingEvents();
        System.out.println("These events are what you're looking for:");
        if(events == null)
            System.out.println("No events found");
        else if(events.size() == 0)
            System.out.println("No events found");
        else {
            for (int i = 0; i < events.size(); i++)
                if ((events.get(i).getDay() == day || day == 0) && (events.get(i).getMonth() == month || month == 0) && (events.get(i).getYear() == year || year == 0))
                    System.out.println((i + 1) + ". " + events.get(i));
        }
    }

    public void searchUsingBudget(int budget) {
        Building building = Building.getBuilding();
        List<Event> events = building.getIncomingEvents();

        System.out.println("These events are suitable for your budget:");
        if(events == null)
            System.out.println("No events found");
        else if(events.size() == 0)
            System.out.println("No events found");
        else {
            for (int i = 0; i < events.size(); i++)
                if (events.get(i).getStartingPrice() <= budget)
                    System.out.println((i + 1) + ". " + events.get(i));
        }
    }

    public void seeAnEvent(int ID) {
        ID -= 1;
        Building building = Building.getBuilding();
        List<Event> events = building.getIncomingEvents();
        if (events == null)
            System.out.println("There is nothing to see!");
        else if (ID>0  && ID < events.size())
            building.getIncomingEvents().get(ID).presentation();
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void showPurchasedTickets() {
        System.out.println("These are the incoming events you bought tickets for:");
        if (futureTickets == null)
            System.out.println("None");
        else if (futureTickets.size() == 0)
            System.out.println("None");
        else{
            //TODO sorteaza futureTickets dupa denumire

            for (Ticket ticket : futureTickets) {
                ticket.print();
                System.out.println();
            }
        }
    }

    public void showOldTickets() {
        System.out.println("These are the old events you bought tickets for:");
        if (oldTickets == null)
            System.out.println("None");
        else if (oldTickets.size() == 0)
            System.out.println("None");
        else {
            //TODO sorteaza oldTickets dupa denumire
            for (Ticket ticket : oldTickets) {
                ticket.print();
                System.out.println();
            }
        }
    }

    public void showFavorites() {
        System.out.println("There are your favorite events :)");
        if (favorites == null)
            System.out.println("None");
        else if (favorites.size() == 0)
            System.out.println("None");
        else {
            for (int i = 0; i < favorites.size(); i++)
                System.out.println((i + 1) + " " + favorites.get(i));
        }
    }

    public boolean addToFavs(int ID){
        ID -= 1;
        List<Event> events = Building.getBuilding().getIncomingEvents();
        if(favorites == null)
            return false;
        if(ID>=0 && ID<events.size()) {
            favorites.add(events.get(ID).getNameEvent());
            return true;
        }
        else
            return false;
    }

    public boolean deleteFromFavs(int ID){
        ID -= 1;
        if(favorites == null)
            return false;
        if(ID>=0 && ID<favorites.size()) {
            favorites.remove(ID);
            return true;
        }
        else
            return false;
    }
}
