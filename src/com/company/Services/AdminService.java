package com.company.Services;

import com.company.Users.Administrator;

import java.util.Scanner;

public class AdminService implements ServiceInterface{
    //singleton (lazy initialization)
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
    @Override
    public void showOptions(Scanner scanner) {
        System.out.println("Menu:");
        System.out.println("1. Add event");
        System.out.println("2. Delete event");
        System.out.println("3. Log out");

        while(true) {
            System.out.print("Your option: ");
            int option = scanner.nextInt();
            if (verifyOption(option)) {
                if (option == 1)
                    admin.addEvent(scanner); //TODO CREATE EVENT
                else if (option == 2)
                    admin.deleteEvent(scanner);
                else if (option == 3) {
                    Registration.logOut();
                    break;
                }
            }
            else
                System.out.println("Enter a valid option number please!");
        }
    }

    public boolean verifyOption(int option){
        return option <= 3 && option >= 1;
    }
}
