package com.company.user;

import com.company.entity.Theatre;
import com.company.entity.Concert;
import com.company.entity.Event;
import com.company.entity.TheatrePlay;
import com.company.entity.ConcertTicket;
import com.company.entity.TheatrePlayTicket;
import com.company.entity.Ticket;

import java.util.*;

public class Customer {
    //singleton (lazy initialization) because it refers to current user
    public static Customer customer;
    private List<String> favorites;
    private List<Ticket> futureTickets;
    private List<Ticket> oldTickets; //TODO (when adding files) when a show is over the corresponding ticket goes to oldTickets section

    private Customer(){
        favorites = new ArrayList<String>();
        futureTickets = new ArrayList<Ticket>();
        oldTickets = new ArrayList<Ticket>();
    }

    public static Customer getCustomer() {
        if (customer == null)
            customer = new Customer();

        return customer;
    }

    public void buyTickets(int id, Scanner scanner)
    {
        if (id == 0) return; //go back to main menu
        id -= 1;
        Theatre theatre = Theatre.getTheatre();
        List<Event> events = theatre.getIncomingEvents();
        if (events == null)
            System.out.println("There are no events available!");
        else if (id < events.size() && id >= 0)
        {
            Event event = events.get(id);
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

    private boolean verifySeat(String seat, Event event){
        char letter;
        int number;

        letter = seat.charAt(0);
        if(seat.length() <= 1)
            return false;

        number = Integer.parseInt(seat.substring(1)) - 1;

        if (number < 0 || number >= event.getHall().getRows())
            return false;

        if (letter - 'A' < 0 || letter - 'A' >= event.getHall().getColumns())
            return false;

        if ((event.getAvailableSeats())[number][letter - 'A'] == 'X')
            return false;

        return true;
    }

    private void buySpecificSeats(List<String>seats, Event event, Scanner scanner){
        double price, total = 0;

        System.out.print("Prices: ");

        for(String seat: seats) {
            if(verifySeat(seat, event) == false) {
                System.out.println("\nSomething went wrong. Please try again!");
                return;
            }
            price = event.calculatePrice(seat);
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

        for(String seat: seats) {
            char letter = seat.charAt(0);
            int number = Integer.parseInt(seat.substring(1)) - 1;
            Ticket t = null;
            event.markSeat(number, letter - 'A');
            price = event.calculatePrice(seat);
            if (event instanceof  Concert)
                t = new ConcertTicket(seat, price, event);

            else if(event instanceof TheatrePlay)
                t = new TheatrePlayTicket(seat, price, event);

            futureTickets.add(t);
        }

        // mark seats as purchased
        int initialAvailableSeats = event.getNoAvailableSeats();
        event.setNoAvailableSeats(initialAvailableSeats - seats.size());

        Collections.sort(futureTickets);

        System.out.println("Done!");
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

    public boolean addToFavs(int id){
        id -= 1;
        List<Event> events = Theatre.getTheatre().getIncomingEvents();
        if(id>=0 && id<events.size()) {
            favorites.add(events.get(id).getNameEvent());
            return true;
        }
        else
            return false;
    }

    public boolean deleteFromFavs(int id){
        id -= 1;
        if(favorites == null)
            return false;
        if(id>=0 && id<favorites.size()) {
            favorites.remove(id);
            return true;
        }
        else
            return false;
    }
}
