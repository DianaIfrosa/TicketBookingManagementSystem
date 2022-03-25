package com.company.Events;

import com.company.Entities.Hall;

public class TheatrePlay extends Event{
    private String typePlay; // comedy, mystery etc.
    private boolean hasIntermission = false; //break(s)
    final int EXTRA_PRICE = 8; //for calculating ticket price based on seat and starting from startingPrice

    public TheatrePlay(Hall hall, double startingPrice, String nameEvent, String description, int day, int month, int year, String startingHour, String endingHour, String typePlay, boolean hasIntermission) {
        super(hall, startingPrice, nameEvent, description, day, month, year, startingHour, endingHour);
        this.typePlay = typePlay;
        this.hasIntermission = hasIntermission;
    }

    public String getTypePlay() {
        return typePlay;
    }

    public void setTypePlay(String typePlay) {
        this.typePlay = typePlay;
    }

    public boolean getHasIntermission() {
        return hasIntermission;
    }

    public void setHasIntermission(boolean hasIntermission) {
        this.hasIntermission = hasIntermission;
    }

    public int getEXTRA_PRICE() {
        return EXTRA_PRICE;
    }
    public double calculatePrice(String seat) {
        double total = this.startingPrice;
        char letter = seat.charAt(0);
        int number = Integer.parseInt(seat.substring(1));
        int row = letter - 'A'; // 0,1,...
        //front seats are more expensive
        total += EXTRA_PRICE * (hall.getRows() - row);
        //middle seats are more expensive
        if (number >= hall.getColumns()*1/3 && number <= hall.getColumns()*2/3 )
            total += EXTRA_PRICE;
        return total;
    }
    public void presentation(){
        System.out.println("Name: " + this.nameEvent);
        System.out.println("Type of play: " + this.typePlay);
        System.out.println("Description: " + this.description);
        System.out.println("Date and hours: " + this.day + "." + this.month + "." + this.year + " "
                            + this.startingHour + "-" + this.endingHour);
        System.out.println("Does it have intermission: " + this.hasIntermission);
    }

}
