package com.company.Services;

import com.company.Users.Administrator;
import com.company.Users.Customer;
import jdk.jfr.consumer.RecordingFile;

public class Menu {
    //singleton (early initialization)
    public static final Menu menu = new Menu();

    private Menu() {}
    public static Menu getMenu() {
        return menu;
    }

    public static boolean verifyOption(int option, String userType) {

        if (userType == "customer" ) {
            if (option <= 10 && option >= 1)
                return true;
            else
                return false;
        }
        else{ //administrator
            if (option <= 4 && option >=1 )
                return true;
            else
                return false;

        }
    }

    public static void menuCustomer(Customer customer)  {

        System.out.println("Menu:");
        System.out.println("1. See next events");
        System.out.println("2. Search for events using date");
        System.out.println("3. Search for tickets based on my budget");
        System.out.println("4. Buy tickets for a specific event");
        System.out.println("5. Show purchased tickets");
        System.out.println("6. Show previously events I attented");
        System.out.println("7. My Favorites list");
        System.out.println("8. Add to My Favorites");
        System.out.println("9. Delete from My Favorites");
        System.out.println("10. Log out");

        while(true)
        {
            System.out.print("Your option: ");
            int option = Integer.parseInt(System.console().readLine());
            if (verifyOption(option, "customer")) {
                if(option == 1)
                   customer.seeNextEvents();
                else if (option == 2) {
                    System.out.print("Month: ");
                    int month = Integer.parseInt(System.console().readLine());
                    System.out.print("Day: ");
                    int day = Integer.parseInt(System.console().readLine());
                    customer.searchUsingDate(month, day);
                }
                else if (option == 3) {
                    System.out.print("Please enter your maximum budget:");
                    int budget = Integer.parseInt(System.console().readLine());
                    customer.searchUsingBudget(budget);
                }
                else if (option == 4);
                else if (option == 5);
                else if (option == 6);
                else if (option == 7);
                else if (option == 8);
                else if (option == 9);
                else if (option == 10) {
                    Registration.logOut();
                    break;
                }
            }
            else
                System.out.println("Enter a valid option number please!");
        }
    }

    public static void menuAdministrator(Administrator administrator) {
        System.out.println("Menu:");
        System.out.println("1. Add event");
        System.out.println("2. Delete event");
        System.out.println("3. Modify event");
        System.out.println("4. Log out");

        while(true) {
            System.out.print("Your option: ");
            int option = Integer.parseInt(System.console().readLine());
            if (verifyOption(option, "customer")) {
                if (option == 1)
                    administrator.addEvent();
                else if (option == 2)
                    administrator.deleteEvent();
                else if (option == 3)
                    administrator.modifyEvent();
                else if (option == 4) {
                    Registration.logOut();
                    break;
                }
            }
            else
                System.out.println("Enter a valid option number please!");
        }
    }

}
