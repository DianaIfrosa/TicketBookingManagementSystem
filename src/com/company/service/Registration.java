package com.company.service;

import com.company.user.Administrator;
import com.company.user.Customer;
import java.util.Scanner;

public class Registration implements IRegistration{
    // singleton (early initialization)
    public static final Registration registration = new Registration();

    public static Registration getRegistration() {
        return registration;
    }

    public int logIn(Scanner scanner) {
        // returns 1 for customer login success, 2 for administrator login success, 0 for login fail
        ReadService readService = ReadService.getReadService();
        System.out.print("Please enter username:");
        String username = scanner.next();
        System.out.print("Please enter password:");
        String password = scanner.next();

        int customerId = readService.checkUser(username, password, "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\customers.csv");
        if (customerId != -1) {
            Customer customer = Customer.getCustomer();
            customer.setId(customerId);
            customer.setUsername(username);
            customer.setPassword(password);
            AuditService.getAuditService().writeAudit("login customer id: " + customerId);
            return 1;
        }

        int administratorId = readService.checkUser(username, password, "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\administrators.csv");
        if (administratorId != -1) {
            Administrator administrator = Administrator.getAdministrator();
            administrator.setId(administratorId);
            administrator.setUsername(username);
            administrator.setPassword(password);
            AuditService.getAuditService().writeAudit("login administrator id: " + administratorId);
            return 2;
        }
        return 0;
    }

    public void logOut(int userId, String username) {
        AuditService.getAuditService().writeAudit("User id: " + userId + " logged out");
        System.out.println("Goodbye " + username + "!");
    }

    public void signUpCustomer(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\customers.csv";
        int id = ReadService.getReadService().nextUserId(path);
        WriteService.getWriteService().addUser(id, username, password, path);
        AuditService.getAuditService().writeAudit("sign up customer id: " + id);
        System.out.println("Register done!");
    }
    public void signUpAdmin(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        String path = "D:\\facultate\\PAO\\TicketBookingManagementSystem\\Files\\administrators.csv";
        int id = ReadService.getReadService().nextUserId(path);
        WriteService.getWriteService().addUser(id, username, password, path);
        AuditService.getAuditService().writeAudit("sign up administrator id: " + id);
        System.out.println("Register done!");
    }
}
