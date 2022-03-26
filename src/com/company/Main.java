package com.company;

import com.company.Entities.Building;
import com.company.Entities.Hall;
import com.company.Events.Concert;
import com.company.Events.Event;
import com.company.Events.TheatrePlay;
import com.company.Services.AdminService;
import com.company.Services.CustomerService;
import com.company.Services.Registration;
import com.company.Users.Administrator;
import com.company.Users.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Building building = Building.getBuilding();
        Registration registration = Registration.getRegistration();

        //MOCK DATA TODO read from files
        Hall hall1 = new Hall("Hall1", 0, 30, true, 6, 5);
        Hall hall2 = new Hall("Hall2", 1, 40, true, 6, 7 );
        Hall hall3 = new Hall("Hall3", 2, 36, true, 5, 8);
        Hall hall4 = new Hall("Hall4", 2, 20, false, 5, 4);

        List<Hall> listHalls = new ArrayList<Hall>();
        listHalls.add(hall1);
        listHalls.add(hall2);
        listHalls.add(hall3);
        listHalls.add(hall4);
        building.setHalls(listHalls);

        Event event1 = new Concert(hall1, 30, "Concert1", "Beautiful concert", 1, 2, 2022, "18:00", "20:00", "classical", false);
        Event event2 = new TheatrePlay(hall2, 12, "TheatrePlay1", "Breathtaking play", 4, 10, 2022, "13:00", "14:20", "classical", false);
        Event event3 = new Concert(hall1, 20.5, "Concert2", "Fun concert", 2, 2, 2022, "17:00", "21:00", "folk", true);

        List<Event> events = new ArrayList<Event>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        building.setIncomingEvents(events);

        building.showBuildingInformation();

        while (true) {
            //register or/and log in
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
            int type = registration.logIn(scanner);
            if (type == 1) {
                //customer
                System.out.println("\n-----------Customer menu-----------");
                Customer customer = Customer.getCustomer();
                CustomerService customerS = CustomerService.getCustomerService(customer);
                customerS.showOptions(scanner);
                break;
            }
            else if (type == 2) {
                //administrator
                System.out.println("\n-----------Administrator menu-----------");
                Administrator administrator = Administrator.getAdministrator();
                AdminService adminS = AdminService.getAdminService(administrator);
                adminS.showOptions(scanner);
                break;
            }
        }
        scanner.close();
    }
}
