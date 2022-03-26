package com.company;

import com.company.Entities.Building;
import com.company.Services.AdminService;
import com.company.Services.CustomerService;
import com.company.Services.Registration;
import com.company.Users.Administrator;
import com.company.Users.Customer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Building building = Building.getBuilding();
        Registration registration = Registration.getRegistration();
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
                System.out.println("-----------Register-----------");
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

            System.out.println("-----------Login-----------");
            int type = registration.logIn(scanner);
            if (type == 1) {
                //customer
                System.out.println("-----------Customer menu-----------");
                Customer customer = Customer.getCustomer();
                CustomerService customerS = CustomerService.getCustomerService(customer);
                customerS.showOptions(scanner);
                break;
            }
            else if (type == 2) {
                //administrator
                System.out.println("-----------Administrator menu-----------");
                Administrator administrator = Administrator.getAdministrator();
                AdminService adminS = AdminService.getAdminService(administrator);
                adminS.showOptions(scanner);
                break;
            }
        }
        scanner.close();
    }
}
