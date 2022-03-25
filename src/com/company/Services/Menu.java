package com.company.Services;

import com.company.Users.Administrator;
import com.company.Users.Customer;
import jdk.jfr.consumer.RecordingFile;

public class Menu {
    //TODO DELETE
    //singleton (early initialization)
    public static final Menu menu = new Menu();

    private Menu() {}
    public static Menu getMenu() {
        return menu;
    }



    public static void menuAdministrator(Administrator administrator) {

    }

}
