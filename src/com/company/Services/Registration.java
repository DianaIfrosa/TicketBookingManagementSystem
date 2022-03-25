package com.company.Services;

public class Registration {
    //singleton (early initialization)
    public static final Registration registration = new Registration();

    public static Registration getRegistration() {
        return registration;
    }

    public static int logIn(){
    // return 1 for customer and 2 for administrator
        //TODO CITIRE DIN FISIER si verificare date
        return 1;

    }

    public static void logOut(){
        System.out.println("Goodbye!");
    }

    public static void signUp()
    {
        //TODO CITIRE DIN FISIER SI ADAUGARE USER
    }
}
