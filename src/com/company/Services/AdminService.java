package com.company.Services;

import com.company.Users.Administrator;
import com.company.Users.Customer;

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
    public void showOptions() {
        System.out.println("Menu:");
        System.out.println("1. Add event");
        System.out.println("2. Delete event");
        System.out.println("3. Modify event");
        System.out.println("4. Log out");

        while(true) {
            System.out.print("Your option: ");
            int option = Integer.parseInt(System.console().readLine());
            if (verifyOption(option)) {
                if (option == 1)
                    admin.addEvent();
                else if (option == 2)
                    admin.deleteEvent();
                else if (option == 3)
                    admin.modifyEvent();
                else if (option == 4) {
                    Registration.logOut();
                    break;
                }
            }
            else
                System.out.println("Enter a valid option number please!");
        }
    }

    public boolean verifyOption(int option){
        if (option <= 4 && option >=1 )
            return true;
        else
            return false;
    }
}
