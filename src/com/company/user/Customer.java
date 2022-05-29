package com.company.user;

import com.company.entity.Theatre;
import com.company.entity.Concert;
import com.company.entity.Event;
import com.company.entity.TheatrePlay;
import com.company.entity.ConcertTicket;
import com.company.entity.TheatrePlayTicket;
import com.company.entity.Ticket;
import com.company.repository.AuditRepository;
import com.company.repository.FavoritesRepository;
import com.company.repository.TicketRepository;

import java.util.*;

public class Customer {
    // singleton (lazy initialization) because it refers to current user
    public static Customer customer;
    private int id;
    private String username;
    private String password;
    private Set<Event> favorites;
    private List<Ticket> futureTickets;
    private List<Ticket> oldTickets;

    private Customer(){
        favorites = new TreeSet<>();
        futureTickets = new ArrayList<>();
        oldTickets = new ArrayList<>();
    }

    public static Customer getCustomer() {
        if (customer == null)
            customer = new Customer();

        return customer;
    }

    public void buyTickets(int id, Scanner scanner) {
        if (id == 0) return; // go back to main menu
        Theatre theatre = Theatre.getTheatre();
        List<Event> events = theatre.getFutureEvents();
        if (events == null)
            System.out.println("There are no events available!");
        else {
            Event event = theatre.findFutureEvent(id);
            if (event == null) {
                System.out.println("ID is not valid. Please try again!");
                return;
            }

                System.out.println("How many tickets do you want to buy?");
            int noTickets = scanner.nextInt();
            if (event.getNoAvailableSeats() == 0) {
                System.out.println("Sorry! This event is sold out.");
                return;
            }
            if (event.getNoAvailableSeats() < noTickets) {
                System.out.println("Sorry! We only have " + event.getNoAvailableSeats() + " left. Do you want those (yes/no)?");
                List<String> seatsChosen = new ArrayList<>();
                while (true) {
                    String ans = scanner.next();
                    if (ans.equalsIgnoreCase("no"))
                        return;
                    if (ans.equalsIgnoreCase("yes")) {
                        List<List<Character>> seats = event.getAvailableSeats();
                        for (int i = 0; i < event.getHall().getRows(); i++)
                            for (int j = 0; j < event.getHall().getColumns(); j++)
                                if (seats.get(i).get(j) == 'O') {
                                    // buy the ticket
                                    StringBuilder seat = new StringBuilder();
                                    char letter = (char) (j + 'A');
                                    seat.append(letter);
                                    seat.append((i + 1));

                                    String seatString = seat.toString();
                                    seatsChosen.add(seatString);
                                }
                        this.buySpecificSeats(seatsChosen, event, scanner);
                        break;
                    } else
                        System.out.println("Please type a valid answer!");
                }
            } else {
                System.out.println("Choose from these options. 'O' means free, 'X' means taken.\n");
                event.showAvailableSeats();
                List<String> seats = new ArrayList<>();

                System.out.println("\nYour seats (such as A13, D5):");
                for (int i = 0; i < noTickets; i++) {
                    System.out.print("Choice no. " + (i + 1) + ": ");
                    String ans = scanner.next();
                    seats.add(ans);
                }

                this.buySpecificSeats(seats, event, scanner);
            }
        }
    }

    private boolean verifySeat(String seat, Event event){
        char letter;
        int number;

        letter = seat.charAt(0);
        if(seat.length() <= 1)
            return false;
        try {
            number = Integer.parseInt(seat.substring(1)) - 1;
        }
        catch (NumberFormatException e) {
           return false;
        }

        if (number < 0 || number >= event.getHall().getRows())
            return false;

        if (letter - 'A' < 0 || letter - 'A' >= event.getHall().getColumns())
            return false;

        if ((event.getAvailableSeats()).get(number).get(letter - 'A') == 'X')
            return false;

        return true;
    }

    private void buySpecificSeats(List<String>seats, Event event, Scanner scanner){
        double price, total = 0;
        List<Double> prices = new ArrayList<>();

        // stream
        Optional<String> wrongSeats = seats.stream()
                .filter(s -> !verifySeat(s,event))
                .findAny();

        if (wrongSeats.isPresent()){
            System.out.println("\nInvalid seat. Please try again!");
            return;
        }

        System.out.print("Prices: ");
        for(String seat: seats) {
            price = event.calculatePrice(seat);
            total += price;
            prices.add(price);
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

        for(int i = 0; i < seats.size(); i++) {

            Ticket t = null;
            event.markSeat(seats.get(i));
            if (event instanceof  Concert)
                t = new ConcertTicket(seats.get(i), prices.get(i), event);

            else if(event instanceof TheatrePlay)
                t = new TheatrePlayTicket(seats.get(i), prices.get(i), event);

            if (t != null) {
                futureTickets.add(t);
                TicketRepository.addTicket(this.id, t);
            }
        }

        // mark seats as purchased
        int initialAvailableSeats = event.getNoAvailableSeats();
        event.setNoAvailableSeats(initialAvailableSeats - seats.size());
        AuditRepository.addToAudit("Customer id: " + this.id + " bought " + seats.size()
                                                 + " ticket(s) for event id: " + event.getEventId());

        // lambda expression for sort
        futureTickets.sort((o1, o2) ->
        {
            int compareResult = o1.getEvent().getNameEvent().compareTo(o2.getEvent().getNameEvent());
            if (compareResult == 0)
                return o1.getSeat().compareTo(o2.getSeat());
            else
                return compareResult;
        });

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
        else
            FavoritesRepository.showUserFavoriteEvents(this.getId());

        System.out.println();
    }

    public boolean addToFavs(int id){
        Event event = Theatre.getTheatre().findFutureEvent(id);
        if (event == null)
            return false;

        // stream
        Optional<Event> eventsFound = favorites.stream()
                .filter(e -> e.getEventId() == id)
                .findAny();

        if (eventsFound.isPresent()){
            System.out.println("This event is already in your favorites list!");
            return false;
        }

        favorites.add(event);
        FavoritesRepository.addFavoriteEvent(this.id, id);
        AuditRepository.addToAudit("Customer id: " + this.id + " added event id: " + id + " to favorites");
        return true;
    }

    public boolean deleteFromFavs(int id){
        if(favorites == null)
            return false;

        Event event = Theatre.getTheatre().findFutureEvent(id);
        if (event == null)
            return false;

        boolean found = false;
        for (Event e:favorites)
            if (e.getEventId() == id) {
                found = true;
                break;
            }

        if (!found)
            return false;

        favorites.remove(event);
        FavoritesRepository.deleteFavoriteEvent(this.id, id);
        AuditRepository.addToAudit("Customer id: " + this.id + " deleted event id: " + id + " from favorites");
        return true;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavorites(Set<Event> favorites) {
        this.favorites = favorites;
    }

    public void setFutureTickets(List<Ticket> futureTickets) {
        this.futureTickets = futureTickets;
    }

    public void setOldTickets(List<Ticket> oldTickets) {
        this.oldTickets = oldTickets;
    }

}
