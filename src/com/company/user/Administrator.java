package com.company.user;

import com.company.entity.Theatre;
import com.company.entity.Hall;
import com.company.entity.Concert;
import com.company.entity.Event;
import com.company.entity.TheatrePlay;
import com.company.service.AuditService;
import com.company.service.WriteService;

import java.util.Scanner;

public class Administrator {
    // singleton (lazy initialization) because it refers to current user
    public static Administrator administrator;
    private  int id;
    private String username;
    private String password;

    private Administrator() {}
    public static Administrator getAdministrator() {

        if (administrator == null)
            administrator = new Administrator();
        return administrator;
    }

    public void addEvent(Scanner scanner) {
        Theatre theatre = Theatre.getTheatre();

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
        Hall[] hallsAvailable = theatre.hallsAvailable(day, month, year);
        for(int i=0; i<hallsAvailable.length; i++)
            System.out.println((i+1) + ". " + hallsAvailable[i].getName());

        System.out.print("Your hall option: ");
        int noHall = scanner.nextInt() - 1;

        if(noHall >= hallsAvailable.length || noHall < 0) {
            System.out.println("Invalid hall! Please try again!\n");
            return;
        }

        System.out.println("Concert or play (c/p)? Type 'e' for exit");
        Event event;
        while(true) {
            char typeEvent = scanner.next().charAt(0); // concert or play
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

                Theatre.noEvents++;
                event = new Concert(Theatre.noEvents, hallsAvailable[noHall], startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, standing);
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
                Theatre.noEvents++;
                event = new TheatrePlay(Theatre.noEvents, hallsAvailable[noHall], startingPrice, eventName, description, day, month, year, startingHour, endingHour, type, intermission);
                break;
            }
            else if (typeEvent == 'e')
                return;
            else
                System.out.println("Please enter a valid option!");
        }

        boolean ok = theatre.addEvent(event);
        if(ok) {
            WriteService.getWriteService().writeEvent(event);
            AuditService.getAuditService().writeAudit("Admin id: " + id + " added event id: " + event.getEventId());
            System.out.println("Event added!\n");
        }
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void deleteEvent(Scanner scanner){
        Theatre theatre = Theatre.getTheatre();

        theatre.showFutureEvents();
        theatre.showPastEvents();

        if (theatre.getFutureEvents()!= null){
            if(theatre.getFutureEvents().size() == 0) {
            return;
            }
        }
        else if (theatre.getFutureEvents() == null)
            return;

        System.out.print("Enter the event ID you would like to delete: ");
        int id = scanner.nextInt();

        Event event = Theatre.theatre.findFutureEvent(id);
        if (event == null)
        {
            event = Theatre.theatre.findPastEvent(id);
            if (event == null) {
                System.out.println("Event not found! Please try again!\n");
                return;
            }
        }

        boolean ok = theatre.deleteEvent(event);
        if(ok) {
            WriteService.getWriteService().deleteEvent(event);
            AuditService.getAuditService().writeAudit("Admin id: " + id + " deleted event id: " + event.getEventId());
            System.out.println("Event deleted!\n");
        }
        else
            System.out.println("Something went wrong! Try again!\n");
    }

    public void seeFutureEvents() {
        Theatre theatre = Theatre.getTheatre();
        theatre.showFutureEvents();
        System.out.println();
    }

    public void seePastEvents() {
        Theatre theatre = Theatre.getTheatre();
        theatre.showPastEvents();
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
