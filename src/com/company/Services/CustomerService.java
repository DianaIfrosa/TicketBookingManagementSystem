package com.company.Services;

import com.company.Users.Customer;

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
    public void showOptions() {

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

        while (true) {
            System.out.print("Your option: ");
            int option = Integer.parseInt(System.console().readLine());
            if (verifyOption(option)) {
                if (option == 1)
                    customer.seeNextEvents();
                else if (option == 2) {
                    System.out.print("Month: ");
                    int month = Integer.parseInt(System.console().readLine());
                    System.out.print("Day: ");
                    int day = Integer.parseInt(System.console().readLine());
                    customer.searchUsingDate(month, day);
                } else if (option == 3) {
                    System.out.print("Please enter your maximum budget:");
                    int budget = Integer.parseInt(System.console().readLine());
                    customer.searchUsingBudget(budget);
                } else if (option == 4) ;
                else if (option == 5) ;
                else if (option == 6) ;
                else if (option == 7) ;
                else if (option == 8) ;
                else if (option == 9) ;
                else if (option == 10) {
                    Registration.logOut();
                    break;
                }
            } else
                System.out.println("Enter a valid option number please!");
        }
    }
        public boolean verifyOption(int option){
            if (option <= 10 && option >= 1)
                return true;
            else
                return false;

        }

}
