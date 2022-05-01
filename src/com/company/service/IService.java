package com.company.service;

import java.io.IOException;
import java.util.Scanner;

public interface IService {
   boolean verifyOption(int option);
   void useMenu(Scanner scanner) throws IOException;
   void showMenu();
}
