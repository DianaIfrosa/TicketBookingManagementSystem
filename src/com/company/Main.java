package com.company;

import com.company.Entities.Building;
import com.company.Services.Menu;
import com.company.Services.Registration;
import com.company.Users.Administrator;
import com.company.Users.Customer;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       /* Scanner scanner = new Scanner(System.in);
        Building building = Building.getBuilding();
        Registration registration = Registration.getRegistration();
        Menu menu = Menu.getMenu();

        building.showBuildingInformation();

        //register or/and log in
        System.out.print("Do you have an account? Type yes or no: ");
        while (true) {

            String ans = scanner.nextLine();
            System.out.println(ans);

            if (! ans.toUpperCase().equals("NO") && ! ans.toUpperCase().equals("YES")){
                System.out.println("Enter a valid answer please!");
                continue;
            }
            if (ans.toUpperCase().equals("NO")) {
                //TODO register
            }
            //TODO login
            // TODO DE DAT PARAMETRI
            int type = registration.logIn();
            if (type == 1) {
                //customer
                System.out.println("Customer here");
                Customer customer = Customer.getCustomer();
                menu.menuCustomer(customer);
                break;
            }
            else if (type == 2) {
                //administrator
                System.out.println("Admin here");
                Administrator administrator = Administrator.getAdministrator();
                menu.menuAdministrator(administrator);
                break;
            }
        }
        scanner.close();
        */
    }
}
