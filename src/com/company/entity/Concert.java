package com.company.entity;

public class Concert extends Event {
    private boolean standing; // whether it is with allocated seat or not
    final int EXTRA_PRICE = 10; // for calculating ticket price based on seat and starting from startingPrice

    public Concert(int id, Hall hall, double startingPrice, String nameEvent, String description, int day, int month, int year, String startingHour, String endingHour, String type, boolean standing) {
        super(id, hall, startingPrice, nameEvent, description, type,  day, month, year, startingHour, endingHour);
        this.type = type;
        this.standing = standing;
    }

    public boolean getStanding() {
        return standing;
    }

    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    public int getEXTRA_PRICE() {
        return EXTRA_PRICE;
    }

    public double calculatePrice(String seat) {
        if(!standing) {
            double total = this.startingPrice;
            char letter = seat.charAt(0);
            int row = letter - 'A'; // 0,1,...
            // front seats are more expensive
            total += EXTRA_PRICE * (hall.getRows() - row);

            return total;
        }
        else
            // for standing concerts the price for everybody is the starting price
            return this.startingPrice;
        }
    public void presentation(){
        System.out.println("Concert: " + this.nameEvent);
        System.out.println("Type of concert: " + this.type);
        System.out.println("Description: " + this.description);
        System.out.println("Date and hours: " + this.day + "." + this.month + "." + this.year + " "
                + this.startingHour + "-" + this.endingHour);
        System.out.println("Is it a standing concert: " + this.standing);
        System.out.println("Starting price: " + this.startingPrice  + "$");
    }
}

