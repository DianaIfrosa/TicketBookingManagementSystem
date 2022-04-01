package com.company.Services;

import com.company.Entities.Theatre;
import com.company.Events.Event;
import com.company.Users.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerService implements IService {
    //singleton (lazy initialization)
    Customer customer;
    public static CustomerService customerService;

    private CustomerService(Customer c) {
        customer = c;
    }
    public static CustomerService getCustomerService(Customer c) {
        if (customerService == null) {
            customerService = new CustomerService(c);
        }
        return customerService;
    }

    public void showMenu() {
        System.out.println("\n-----------Customer menu-----------");
        System.out.println("1. See future events");
        System.out.println("2. Search for events using date");
        System.out.println("3. Search for tickets based on my budget");
        System.out.println("4. Buy tickets for a specific event");
        System.out.println("5. Show purchased tickets");
        System.out.println("6. Show previously events I attented");
        System.out.println("7. My Favorites list");
        System.out.println("8. Add to My Favorites");
        System.out.println("9. Delete from My Favorites");
        System.out.println("10. See details of an event");
        System.out.println("11. Log out");
    }

    public void useMenu(Scanner scanner) {

        while (true) {
            this.showMenu();
            System.out.print("Your option: ");

            int option = scanner.nextInt();
            if (verifyOption(option)) {
                if (option == 1) {
                    System.out.println("\n-----------Future events-----------");
                    seeFutureEvents();
                }
                else if (option == 2) {
                    System.out.println("\n-----------Search using date-----------");
                    System.out.println("If you do not have preferences for a field, please type 0!");
                    System.out.print("Day: ");
                    int day = scanner.nextInt();
                    System.out.print("Month: ");
                    int month = scanner.nextInt();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    searchUsingDate(day, month, year);
                }
                else if (option == 3) {
                    System.out.println("\n-----------Search based on budget-----------");
                    System.out.print("Please enter your maximum budget:");
                    int budget = scanner.nextInt();
                    searchUsingBudget(budget);
                }
                else if (option == 4){
                    System.out.println("\n-----------Buy tickets-----------");
                    System.out.print("Type the event ID you would like to attend. If you need to see the events again please type 0 and you will go back to the main menu.\n" +
                                       "Your choice: ");
                    int ID = scanner.nextInt();
                    System.out.println();
                    customer.buyTickets(ID, scanner);
                }
                else if (option == 5) {
                    System.out.println("\n-----------See purchased tickets-----------");
                    customer.showPurchasedTickets();
                }
                else if (option == 6) {
                    System.out.println("\n-----------Show old tickets-----------");
                    customer.showOldTickets();
                }
                else if (option == 7) {
                    System.out.println("\n-----------Your favorites-----------");
                    customer.showFavorites();
                }
                else if (option == 8){
                    System.out.println("\n-----------Add to favorites list-----------");
                    System.out.println("Please type the show ID you would like to add to your list!");
                    System.out.println("See all shows again?(yes/no/exit)");
                    boolean exit = false;
                    while(true) {
                        String ans = scanner.next();
                        if (ans.equalsIgnoreCase("yes")) {
                            seeFutureEvents();
                            break;
                        }
                        else if (ans.equalsIgnoreCase("no"))
                            break;
                        else if (ans.equalsIgnoreCase("exit")) {
                            exit = true;
                            break;
                        }
                        else
                            System.out.println("Type yes or no!");
                    }
                    if (exit)
                        continue;

                    System.out.print("Your show ID:");
                    int id = scanner.nextInt();
                    boolean ok = customer.addToFavs(id);
                    if (ok)
                        System.out.println("Event added to favorites!");
                    else
                        System.out.println("Something went wrong. Please try again!");
                }
                else if (option == 9){
                    System.out.println("\n-----------Remove from favorites list-----------");
                    System.out.println("Please type the show ID you would like to remove from your list!");
                    System.out.println("See your favorites list again?(yes/no/exit)");
                    boolean exit = false;
                    while (true) {
                        String ans = scanner.next();
                        if (ans.equalsIgnoreCase("yes")) {
                            customer.showFavorites();
                            break;
                        }
                        else if (ans.equalsIgnoreCase("no"))
                            break;
                        else if (ans.equalsIgnoreCase("exit")) {
                            exit = true;
                            break;
                        }
                        else
                            System.out.println("Type yes or no!");
                    }
                    if (exit)
                        continue;

                    System.out.print("Your show ID:");
                    int id = scanner.nextInt();
                    boolean ok = customer.deleteFromFavs(id);
                    if (ok)
                        System.out.println("Event removed from favorites!");
                    else
                        System.out.println("Something went wrong. Please try again!");
                }
                else if (option == 10){
                    System.out.println("\n-----------See details for a show-----------");
                    System.out.println("See all shows again?(yes/no/exit)");
                    boolean exit = false;
                    while(true) {
                        String ans = scanner.next();
                        if (ans.equalsIgnoreCase("yes")) {
                            seeFutureEvents();
                            break;
                        }
                        else if (ans.equalsIgnoreCase("no"))
                            break;
                        else if (ans.equalsIgnoreCase("exit")) {
                            exit = true;
                            break;
                        }
                        else
                            System.out.println("Type yes or no!");
                    }
                    if (exit)
                        continue;

                    System.out.print("Please enter the ID of the event you want to see: ");
                    int ID = scanner.nextInt();
                    seeAnEvent(ID);
                }
                else if (option == 11) {
                    System.out.println("\n-----------Logout-----------");
                    Registration.getRegistration().logOut();
                    break;
                }
            }
            else
                System.out.println("Enter a valid option please!");
        }
    }

    private void searchUsingDate(int day, int month, int year) {
        Theatre theatre = Theatre.getTheatre();
        List<Event> events = theatre.getIncomingEvents();
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
        Theatre theatre = Theatre.getTheatre();
        List<Event> events = theatre.getIncomingEvents();

        System.out.println("These events are suitable for your budget:");
        if(events == null)
            System.out.println("No events found");
        else if(events.size() == 0)
            System.out.println("No events found");
        else {
            boolean found = false;
            for (int i = 0; i < events.size(); i++)
                if (events.get(i).getStartingPrice() <= budget) {
                    System.out.println((i + 1) + ". " + events.get(i));
                    found = true;
                }
            if(found == false)
                System.out.println("No events found");
        }
        System.out.println();
    }

    public void seeFutureEvents() {
        Theatre theatre = Theatre.getTheatre();
        theatre.showFutureEvents();
        System.out.println();
    }

    public void seeAnEvent(int ID) {
        ID -= 1;
        Theatre theatre = Theatre.getTheatre();
        List<Event> events = theatre.getIncomingEvents();
        if (events == null)
            System.out.println("There is nothing to see!");
        else if (ID>=0  && ID < events.size())
            theatre.getIncomingEvents().get(ID).presentation();
        else
            System.out.println("Something went wrong! Try again!");
        System.out.println();
    }

    public boolean verifyOption(int option){
            return option <= 11 && option >= 1;
        }
}
