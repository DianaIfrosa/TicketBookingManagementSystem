package com.company.service;

import com.company.user.Administrator;

import java.util.Scanner;

public class AdminService implements IService {
    // singleton (lazy initialization)
    Administrator admin;
    public static AdminService adminService;

    private AdminService(Administrator admin) {
        this.admin = admin;
    }
    public static AdminService getAdminService(Administrator admin) {
        if (adminService == null) {
            adminService = new AdminService(admin);
        }
        return adminService;
    }
    public void showMenu() {
        System.out.println("\n-----------Administrator menu-----------");
        System.out.println("1. See all events");
        System.out.println("2. Add event");
        System.out.println("3. Delete event");
        System.out.println("4. Log out");
    }

    @Override
    public void useMenu(Scanner scanner){

        while(true) {
            showMenu();
            System.out.print("Your option: ");
            int option = scanner.nextInt();
            if (verifyOption(option)) {
                if (option == 1){
                    System.out.println("\n-----------Future events-----------");
                    admin.seeFutureEvents();
                    System.out.println("\n-----------Past events-----------");
                    admin.seePastEvents();
                }
                else if (option == 2) {
                    System.out.println("\n-----------Add event-----------");
                    admin.addEvent(scanner);
                }
                else if (option == 3) {
                    System.out.println("\n-----------Delete event-----------");
                    admin.deleteEvent(scanner);
                }
                else if (option == 4) {
                    System.out.println("\n-----------Logout-----------");
                    Registration.getRegistration().logOut(Administrator.getAdministrator().getId(), Administrator.getAdministrator().getUsername());
                    break;
                }
            }
            else
                System.out.println("Enter a valid option number please!");
        }
    }

    public boolean verifyOption(int option){
        return option <= 4 && option >= 1;
    }
}
