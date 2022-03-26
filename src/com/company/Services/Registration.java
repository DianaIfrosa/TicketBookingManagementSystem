package com.company.Services;

import java.util.Scanner;

public class Registration {
    //singleton (early initialization)
    public static final Registration registration = new Registration();

    public static Registration getRegistration() {
        return registration;
    }

    public int logIn(Scanner scanner){
        // return 1 for customer, 2 for administrator and 0 for fail
        //TODO CITIRE DIN FISIER si verificare date
        String correctNameA = "1234"; //TODO CITIRE FISIER
        String correctPassA = "ABCD";
        String correctNameC = "123"; //TODO CITIRE FISIER
        String correctPassC = "ABC";

        System.out.print("Please enter username:");
        String username = scanner.next();
        System.out.print("Please enter password:");
        String pass = scanner.next();

        if(username.equals(correctNameC) && pass.equals(correctPassC)) {
            System.out.println("Success!");
            return 1;
        }
        if(username.equals(correctNameA) && pass.equals(correctPassA)) {
            System.out.println("Success!");
            return 2;
        }
        System.out.println("Login failed! Please try again!\n");
        return 0;
    }

    public static void logOut(){
        System.out.println("Goodbye!");
    }

    public void signUpCustomer(Scanner scanner)
    {
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String pass = scanner.next();

        System.out.println("Register done!");

        //TODO WRITE TO CUSTOMER FILE
    }
    public void signUpAdmin(Scanner scanner)
    {
        //TODO CITIRE DIN FISIER UN COD PE CARE SA IL FOLOSEASCA CA SA SE VF CA E ADMIN SI ADAUGARE USER
        System.out.print("Enter your ID: ");
        String id = scanner.next();
        System.out.print("Enter your password: ");
        String pass = scanner.next();

        System.out.println("Register done!");

        //TODO WRITE TO ADMIN FILE
    }

}
