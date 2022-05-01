package com.company.service;

import java.util.Scanner;

public interface IRegistration {
    int logIn(Scanner scanner);
    void logOut();
    void signUpAdmin(Scanner scanner);
    void signUpCustomer(Scanner scanner);
}
