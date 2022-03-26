package com.company.Services;

import com.company.Users.Customer;

import java.util.Scanner;

public class CustomerService implements ServiceInterface{
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
    public void showOptions(Scanner scanner) {

        System.out.println("Menu:");
        System.out.println("1. See incoming events");
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

        while (true) {
            System.out.print("Your option: ");
            int option = scanner.nextInt();
            if (verifyOption(option)) {
                if (option == 1)
                    customer.seeIncomingEvents();
                else if (option == 2) {
                    System.out.println("If you do not have preferences for a field, please type 0!");
                    System.out.print("Day: ");
                    int day = scanner.nextInt();
                    System.out.print("Month: ");
                    int month = scanner.nextInt();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    customer.searchUsingDate(day, month, year);
                }
                else if (option == 3) {
                    System.out.print("Please enter your maximum budget:");
                    int budget = scanner.nextInt();
                    customer.searchUsingBudget(budget);
                }
                else if (option == 4); //TODO
                else if (option == 5) {
                    customer.showPurchasedTickets();
                }
                else if (option == 6) {
                    customer.showOldTickets();
                }
                else if (option == 7) {
                    customer.showFavorites();
                }
                else if (option == 8){
                    System.out.println("Please type the show ID you would like to add to your list!");
                    System.out.println("See all shows again?(yes/no)");
                    while(true) {
                        String ans = scanner.next();
                        if (ans.equals("yes")) {
                            customer.seeIncomingEvents();
                            break;
                        }
                        else if (ans.equals("no"))
                            break;
                        else
                            System.out.println("Type yes or no!");
                    }

                    System.out.print("Your show ID:");
                    int id = scanner.nextInt();
                    boolean ok = customer.addToFavs(id);
                    if (ok)
                        System.out.println("Done!");
                    else
                        System.out.println("Something went wrong. Please try again!");
                }
                else if (option == 9){
                    System.out.println("Please type the show ID you would like to remove from your list!");
                    System.out.println("See your favorites list again?(yes/no)");
                    while (true) {
                        String ans = scanner.nextLine();
                        if (ans.equals("yes")) {
                            customer.showFavorites();
                            break;
                        }
                        else if (ans.equals("no"))
                            break;
                        else
                            System.out.println("Type yes or no!");
                    }

                    System.out.print("Your show ID:");
                    int id = scanner.nextInt();
                    boolean ok = customer.deleteFromFavs(id);
                    if (ok)
                        System.out.println("Done!");
                    else
                        System.out.println("Something went wrong. Please try again!");
                }
                else if (option == 10){
                    System.out.println("Please enter the ID of the event: ");
                    int ID = scanner.nextInt();
                    customer.seeAnEvent(ID);
                }
                else if (option == 11) {
                    Registration.logOut();
                    break;
                }
            } else
                System.out.println("Enter a valid option please!");
        }
    }
        public boolean verifyOption(int option){
            return option <= 11 && option >= 1;
        }
}
