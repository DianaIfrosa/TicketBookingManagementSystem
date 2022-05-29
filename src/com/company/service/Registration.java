package com.company.service;

import com.company.database.DatabaseManager;
import com.company.repository.AdministratorRepository;
import com.company.repository.AuditRepository;
import com.company.repository.CustomerRepository;
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
        System.out.print("Please enter username:");
        String username = scanner.next();
        System.out.print("Please enter password:");
        String password = scanner.next();

        int customerId = CustomerRepository.verifyCustomer(username, password);
        if (customerId != -1) {
            Customer customer = Customer.getCustomer();
            customer.setId(customerId);
            customer.setUsername(username);
            customer.setPassword(password);
            AuditRepository.addToAudit("login customer id: " + customerId);
            return 1;
        }

        int administratorId = AdministratorRepository.verifyAdministrator(username, password);
        if (administratorId != -1) {
            Administrator administrator = Administrator.getAdministrator();
            administrator.setId(administratorId);
            administrator.setUsername(username);
            administrator.setPassword(password);
            AuditRepository.addToAudit("login administrator id: " + administratorId);
            return 2;
        }
        return 0;
    }

    public void logOut(int userId, String username) {
        AuditRepository.addToAudit("User id: " + userId + " logged out");
        System.out.println("Goodbye " + username + "!");
    }

    public void signUpCustomer(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        while (AdministratorRepository.usernameTaken(username))
        {
            System.out.println("This username is taken! Please choose another one:");
            username = scanner.next();
        }
        System.out.print("Enter your password: ");
        String password = scanner.next();
        CustomerRepository.addCustomer(username, password);
        int id = DatabaseManager.lastIdFromTable("customer");
        AuditRepository.addToAudit("sign up customer id: " + id);
        System.out.println("Register done!");
    }

    public void signUpAdmin(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        while (AdministratorRepository.usernameTaken(username))
        {
            System.out.println("This username is taken! Please choose another one:");
            username = scanner.next();
        }
        System.out.print("Enter your password: ");
        String password = scanner.next();

        AdministratorRepository.addAdmin(username, password);
        int id = DatabaseManager.lastIdFromTable("administrator");
        AuditRepository.addToAudit("sign up administrator id: " + id);
        System.out.println("Register done!");
    }
}
