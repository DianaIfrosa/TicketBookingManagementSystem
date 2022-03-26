package com.company.Users;

import com.company.Entities.Building;
import com.company.Entities.Hall;
import com.company.Events.Concert;
import com.company.Events.Event;
import com.company.Events.TheatrePlay;

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

        System.out.print("Enter event name: ");
        String eventName = scanner.next();
        System.out.print("Enter starting price ($): ");
        double startingPrice = scanner.nextDouble();
        System.out.print("Enter event description: ");
        String description = scanner.next();
        System.out.print("Enter event type: ");
        String type = scanner.next();
        System.out.print("Enter event starting hour: ");
        String startingHour = scanner.next();
        System.out.print("Enter event ending hour: ");
        String endingHour = scanner.next();
        System.out.print("Enter event day: ");
        int day = scanner.nextInt();
        System.out.print("Enter event month: ");
        int month = scanner.nextInt();
        System.out.print("Enter event year: ");
        int year = scanner.nextInt();

        System.out.print("Halls available: ");
        Hall[] hallsAvailable = building.hallsAvailable(day, month, year);
        for(int i=0; i<hallsAvailable.length; i++)
            System.out.println(i + ". " + hallsAvailable[i].getName());

        System.out.print("Your hall option: ");
        int noHall = scanner.nextInt();
        if(noHall >= hallsAvailable.length || noHall < 0) {
            System.out.println("Invalid hall! Please try again\n!");
            return;
        }

        System.out.println("Concert or play (c/p)? Type 'e' for exit");
        Event event;
        while(true) {
            char typeEvent = scanner.next().charAt(0); //concert or play
            if(typeEvent == 'c') {
                boolean standing;
                System.out.println("It is a standing one (yes/no)?");
                while(true) {
                    String ansStanding = scanner.next();
                    if (ansStanding.equalsIgnoreCase("yes")) {
                        standing = true;
                        break;
                    }
                    else if (ansStanding.equalsIgnoreCase("no")) {
                        standing = false;
                        break;
                    }
                    else
                        System.out.println("Invalid option! Type yes or no.");
                    }
                event = new Concert(hallsAvailable[noHall], startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, standing);
                break;
            }
            else if (typeEvent == 'p') {
                boolean intermission;
                System.out.println("Does it have intermission (yes/no)?");
                while(true) {
                String ansIntermission = scanner.next();
                if(ansIntermission.equalsIgnoreCase("yes")) {
                    intermission = true;
                    break;
                }
                else if(ansIntermission.equalsIgnoreCase("no")) {
                    intermission = false;
                    break;
                }
                else
                    System.out.println("Invalid option! Type yes or no.");
                }
                event = new TheatrePlay(hallsAvailable[noHall], startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, intermission);
                break;
            }
            else if (typeEvent == 'e')
                return;
            else
                System.out.println("Please enter a valid option!");
        }

        boolean ok = building.addEvent(event);
        if(ok)
            System.out.println("Event added!\n");
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void deleteEvent(Scanner scanner){
        //TODO DELETE FROM FILE
        Building building = Building.getBuilding();
        building.showFutureEvents();
        if (building.getIncomingEvents()!= null){
            if(building.getIncomingEvents().size() == 0) {
            return;
            }
        }
        else if (building.getIncomingEvents() == null)
            return;

        System.out.print("Enter the event ID you would like to delete: ");
        int ID = scanner.nextInt();

        boolean ok = building.deleteEvent(ID);
        if(ok)
            System.out.println("Event deleted!\n");
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void seeFutureEvents() {
        Building building = Building.getBuilding();
        building.showFutureEvents();
        System.out.println();
    }
}
