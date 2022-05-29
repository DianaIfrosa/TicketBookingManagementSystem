package com.company;
import com.company.database.DatabaseManager;
import com.company.entity.*;
import com.company.service.*;
import com.company.user.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Theatre theatre = Theatre.getTheatre();
        Registration registration = Registration.getRegistration();

        DatabaseManager manager = DatabaseManager.getDatabaseManager(); //database is initialized when manager is created
        manager.initTheatre();

        theatre.showTheatreInformation();

        while (true) {
            // register or/and log in
            System.out.print("Do you have an account (yes/no)? ");
            String ans = scanner.next();

            if (! ans.equalsIgnoreCase("no") && ! ans.equalsIgnoreCase("yes")){
                System.out.println("Enter a valid answer please!");
                continue;
            }

            if (ans.equalsIgnoreCase("no")) {
                System.out.println("\n-----------Register-----------");
                System.out.print("Are you a client or an administrator (c/a)? ");
                while(true) {
                    char type = scanner.next().charAt(0);
                    if(type == 'a') {
                        registration.signUpAdmin(scanner);
                        break;
                    }
                    else if (type == 'c') {
                        registration.signUpCustomer(scanner);
                        break;
                    }
                    else
                        System.out.println("Please enter a valid option!");
                }
            }

            System.out.println("\n-----------Login-----------");
            int res = registration.logIn(scanner);
            if (res == 1) {
                CustomerService customerS = CustomerService.getCustomerService(Customer.getCustomer());
                manager.initCustomer();
                customerS.useMenu(scanner);
                break;
            }
            else if (res == 2){
                AdminService adminS = AdminService.getAdminService(Administrator.getAdministrator());
                adminS.useMenu(scanner);
                break;
            }
            else{
                System.out.println("Login failed! Please try again!");
            }
        }
        scanner.close();
    }
}