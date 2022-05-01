package com.company.service;

import java.io.IOException;
import java.util.Scanner;

public interface IRegistration {
    int logIn(Scanner scanner)  throws IOException;
    void logOut(int userId, String username) throws  IOException;
    void signUpAdmin(Scanner scanner) throws IOException;
    void signUpCustomer(Scanner scanner) throws IOException;
}
