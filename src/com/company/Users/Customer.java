package com.company.Users;

import com.company.Entities.Building;
import com.company.Events.Concert;
import com.company.Events.Event;
import com.company.Events.TheatrePlay;
import com.company.Tickets.ConcertTicket;
import com.company.Tickets.TheatrePlayTicket;
import com.company.Tickets.Ticket;

import java.util.*;

public class Customer {
    //singleton (lazy initialization) because it refers to current user
    public static Customer customer;
    private List<String> favorites;
    private TreeSet<Ticket > futureTickets, oldTickets; //when a show is over the corresponding ticket goes to oldTickets section

    private Customer(){
        favorites = new ArrayList<String>();
        futureTickets = new TreeSet<Ticket>(new TicketNameComp());
        oldTickets = new TreeSet<Ticket>(new TicketNameComp());
    }
    public static Customer getCustomer() {
        if (customer == null)
            customer = new Customer();

        return customer;
    }

    public void seeFutureEvents() {
        Building building = Building.getBuilding();
        building.showFutureEvents();
        System.out.println();
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
            boolean found = false;
            for (int i = 0; i < events.size(); i++)
                if ((events.get(i).getDay() == day || day == 0) && (events.get(i).getMonth() == month || month == 0) && (events.get(i).getYear() == year || year == 0)) {
                    System.out.println((i + 1) + ". " + events.get(i));
                    found = true;
                }
            if (found == false)
                System.out.println("No events found!");
        }
        System.out.println();
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
        System.out.println();
    }

    public void buyTickets(int ID, Scanner scanner)
    {
        if (ID == 0) return; //go back to main menu
        ID -= 1;
        Building building = Building.getBuilding();
        List<Event> events = building.getIncomingEvents();
        if (events == null)
            System.out.println("There are no events available!");
        else if (ID < events.size() && ID >= 0)
        {
            Event event = events.get(ID);
            System.out.println("How many tickets do you want to buy?");
            int noTickets = scanner.nextInt();
            if(event.getNoAvailableSeats() == 0) {
                System.out.println("Sorry! This event is sold out.");
                return;
            }
            if(event.getNoAvailableSeats() < noTickets) {
                System.out.println("Sorry! We only have " + event.getNoAvailableSeats() + " left. Do you want those (yes/no)?");
                List<String> seatsChosen= new ArrayList<String>();
                while(true) {
                    String ans = scanner.next();
                    if (ans.equalsIgnoreCase("no"))
                        return;
                    if (ans.equalsIgnoreCase("yes")) {
                        Ticket t = null;
                        char[][] seats = event.getAvailableSeats();
                        for(int i=0; i<event.getHall().getRows(); i++)
                            for(int j=0; j<event.getHall().getColumns(); j++)
                                if(seats[i][j] == 'O') {
                                    //buy the ticket
                                    StringBuilder seat= new StringBuilder();
                                    char letter = (char)(j + 'A');
                                    seat.append(letter);
                                    seat.append((i+1));

                                    String seatString = seat.toString();
                                    seatsChosen.add(seatString);
                                }
                        this.buySpecificSeats(seatsChosen, event, scanner);
                        break;
                    }
                    else
                        System.out.println("Please type a valid answer!");
                }
            }
            else{
                System.out.println("Choose from these options. 'O' means free, 'X' means taken.\n");
                event.showAvailableSeats();
                List<Ticket> tickets = new ArrayList<Ticket>();
                List<String> seats = new ArrayList<String>();

                System.out.println("\nYour seats (such as A13, D5):");
                for(int i=0; i<noTickets; i++) {
                    System.out.print("Choice no. " + (i+1) + ": ");
                    String ans = scanner.next();
                    seats.add(ans);
                }

                this.buySpecificSeats(seats, event, scanner);
                }
            }
        else
            System.out.println("ID is not valid. Please try again!");
    }


    private void buySpecificSeats(List<String>seats, Event event, Scanner scanner){
        double price, total = 0;
        char letter;
        int number;

        System.out.print("Prices: ");

        for(int i=0; i<seats.size(); i++) {
            letter = seats.get(i).charAt(0);
            if(seats.get(i).length() <= 1) {
                System.out.println("\nSomething went wrong. Please try again!");
                return;
            }

            number = Integer.parseInt(seats.get(i).substring(1)) - 1;

            if (number < 0 || number >= event.getHall().getRows()) {
                System.out.println("\nSomething went wrong. Please try again!");
                return;
            }
            if (letter - 'A' < 0 || letter - 'A' >= event.getHall().getColumns()){
                System.out.println("\nSomething went wrong. Please try again!");
                return;
            }
            if ((event.getAvailableSeats())[number][letter - 'A'] == 'X') {
                System.out.println("\nYou selected a taken seat. Please try again!");
                return;
            }

            price = event.calculatePrice(seats.get(i));
            total += price;
            System.out.print(price + "$ ");
        }

        System.out.println("\nTotal: " + total + "$");
        System.out.println("Do you agree? (yes/no)");
        while(true) {
            String ans = scanner.next();
            if (ans.equalsIgnoreCase("no"))
                return;
            if (ans.equalsIgnoreCase("yes"))
                break;
            else
                System.out.println("Please type a valid answer!");
        }
        for(int i=0; i<seats.size(); i++) {
            letter = seats.get(i).charAt(0);
            number = Integer.parseInt(seats.get(i).substring(1)) - 1;
            Ticket t = null;
            event.markSeat(number, letter - 'A');
            price = event.calculatePrice(seats.get(i));
            if (event instanceof  Concert)
                t = new ConcertTicket(seats.get(i), price, event);

            else if(event instanceof TheatrePlay)
                t = new TheatrePlayTicket(seats.get(i), price, event);

            futureTickets.add(t);

        }
        // mark seats as purchased
        int initialAvailableSeats = event.getNoAvailableSeats();
        event.setNoAvailableSeats(initialAvailableSeats - seats.size());

        System.out.println("Done!");
    }

    public void seeAnEvent(int ID) {
        ID -= 1;
        Building building = Building.getBuilding();
        List<Event> events = building.getIncomingEvents();
        if (events == null)
            System.out.println("There is nothing to see!");
        else if (ID>=0  && ID < events.size())
            building.getIncomingEvents().get(ID).presentation();
        else
            System.out.println("Something went wrong! Try again!");
        System.out.println();
    }

    public void showPurchasedTickets() {
        System.out.println("These are the incoming events you bought tickets for:");
        if (futureTickets == null)
            System.out.println("None");
        else if (futureTickets.size() == 0)
            System.out.println("None");
        else{
            for (Ticket ticket : futureTickets) {
                ticket.print();
                System.out.println();
            }
        }
        System.out.println();
    }

    public void showOldTickets() {
        System.out.println("These are the old events you bought tickets for:");
        if (oldTickets == null)
            System.out.println("None");
        else if (oldTickets.size() == 0)
            System.out.println("None");
        else {
            for (Ticket ticket : oldTickets) {
                ticket.print();
                System.out.println();
            }
        }
        System.out.println();
    }

    public void showFavorites() {
        System.out.println("These are your favorite events :)");
        if (favorites == null)
            System.out.println("None");
        else if (favorites.size() == 0)
            System.out.println("None");
        else {
            for (int i = 0; i < favorites.size(); i++)
                System.out.println((i + 1) + ". " + favorites.get(i));
        }
        System.out.println();
    }

    public boolean addToFavs(int ID){
        ID -= 1;
        List<Event> events = Building.getBuilding().getIncomingEvents();
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

class TicketNameComp implements Comparator<Ticket> {

    @Override
    public int compare(Ticket t1, Ticket t2) {
        int res = t1.getEvent().getNameEvent().compareTo(t2.getEvent().getNameEvent());
        if (res != 0)
            return res;
        else
            return t1.getSeat().compareTo(t2.getSeat());
    }
}
