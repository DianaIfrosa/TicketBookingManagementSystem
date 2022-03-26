package com.company.Users;

import com.company.Entities.Building;
import com.company.Entities.Hall;
import com.company.Events.Concert;
import com.company.Events.Event;
import com.company.Events.TheatrePlay;

import java.util.List;
import java.util.Scanner;

public class Administrator {
    //singleton (lazy initialization) because it refers to current user
    public static Administrator administrator;

    private Administrator() {}
    public static Administrator getAdministrator() {

        if (administrator == null)
            administrator = new Administrator();
        return administrator;
    }

    public void addEvent(Scanner scanner) {
        //TODO ADD TO FILE
        Building building = Building.getBuilding();
        List<Hall> halls = building.getHalls();

        System.out.print("Halls available: ");
        boolean anyAvailable = false;
        if (halls == null) {
            System.out.println("No hall available.");
            return;
        }
        for(int i=0; i<halls.size(); i++)
            if(halls.get(i).isAvailable()) {
                System.out.print(i + " ");
                anyAvailable = true;
            }

        if (anyAvailable == false) {
            System.out.println("No hall available.");
            return;
        }
        System.out.print("Your hall option: ");
        int noHall = scanner.nextInt();
        if(! halls.get(noHall).isAvailable() || noHall >= halls.size() || noHall < 0) {
            System.out.println("Invalid hall! Please try again!");
            return;
        }
        //double startingPrice, String nameEvent, String description, String type,
        //int day, int month, int year, String startingHour, String endingHour
        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter starting price ($): ");
        double startingPrice = scanner.nextDouble();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        System.out.print("Enter event type: ");
        String type = scanner.nextLine();
        System.out.print("Enter event starting hour: ");
        String startingHour = scanner.nextLine();
        System.out.print("Enter event ending hour: ");
        String endingHour = scanner.nextLine();
        System.out.print("Enter event day: ");
        int day = scanner.nextInt();
        System.out.print("Enter event month: ");
        int month = scanner.nextInt();
        System.out.print("Enter event year: ");
        int year = scanner.nextInt();

        System.out.println("Concert or play (c/p)? Type 'e' for exit");
        Event event;
        while(true) {
            char typeEvent = scanner.next().charAt(0); //concert or play
            if(typeEvent == 'c') {
                System.out.println("It is a standing one (yes/no)?");
                String ansStanding = scanner.next();
                boolean standing;
                if(ansStanding.equals("yes"))
                    standing = true;
                else if(ansStanding.equals("no"))
                    standing = false;
                else {
                    System.out.println("Invalid option. Please try again!");
                    return;
                }

                event = new Concert(halls.get(noHall), startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, standing);
                break;
            }
            else if (typeEvent == 'p') {
                System.out.println("Does it have intermission (yes/no)?");
                String ansIntermission = scanner.next();
                boolean intermission;
                if(ansIntermission.equals("yes"))
                    intermission = true;
                else if(ansIntermission.equals("no"))
                    intermission = false;
                else {
                    System.out.println("Invalid option. Please try again!");
                    return;
                }
                event = new TheatrePlay(halls.get(noHall), startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, intermission);
                break;
            }
            else if (typeEvent == 'e')
                return;
            else
                System.out.println("Please enter a valid option!");
        }

        boolean ok = building.addEvent(event);
        if(ok)
            System.out.println("Done!\n");
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void deleteEvent(Scanner scanner){
        //TODO DELETE FROM FILE
        Building building = Building.getBuilding();
        building.showIncomingEvents();

        System.out.print("Enter the event ID you would like to remove: ");
        int ID = scanner.nextInt();

        boolean ok = building.deleteEvent(ID);
        if(ok)
            System.out.println("Done!\n");
        else
            System.out.println("Something went wrong! Try again!\n");
    }
}
