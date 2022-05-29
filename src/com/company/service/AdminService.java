package com.company.service;

import com.company.repository.AdministratorRepository;
import com.company.repository.AuditRepository;
import com.company.repository.CustomerRepository;
import com.company.repository.HallRepository;
import com.company.user.Administrator;
import com.company.user.Customer;

import java.sql.SQLOutput;
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
        System.out.println("4. See all halls");
        System.out.println("5. Add hall");
        System.out.println("6. Delete hall");
        System.out.println("7. Delete my account");
        System.out.println("8. Log out");
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
                    AuditRepository.addToAudit("Admin id: " + admin.getId() + " viewed all events");
                }
                else if (option == 2) {
                    System.out.println("\n-----------Add event-----------");
                    admin.addEvent(scanner);
                }
                else if (option == 3) {
                    System.out.println("\n-----------Delete event-----------");
                    admin.deleteEvent(scanner);
                }
                else if (option == 4){
                    System.out.println("\n-----------Halls-----------");
                    HallRepository.showHalls();
                    AuditRepository.addToAudit("Admin id: " + admin.getId() + " viewed all halls");
                }
                else if (option == 5){
                    System.out.println("\n-----------Add hall-----------");
                    admin.addHall(scanner);
                }
                else if (option == 6){
                    System.out.println("\n-----------Delete hall-----------");
                    admin.deleteHall(scanner);
                }

                else if (option == 7){
                    AdministratorRepository.deleteAdministrator(Administrator.getAdministrator().getUsername());
                    System.out.println("\nAccount deleted!");
                    System.out.println("Automatically logging you out...");
                    Registration.getRegistration().logOut(admin.getId(), admin.getUsername());
                    break;
                }
                else if (option == 8) {
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
        return option <= 8 && option >= 1;
    }
}
