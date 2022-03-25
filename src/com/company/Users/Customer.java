package com.company.Users;

public class Customer {
    //singleton (lazy initialization) because it refers to current user
    public static Customer customer;

    private Customer(){}
    public static Customer getCustomer() {
        if (customer == null)
            customer = new Customer();

        return customer;
    }

    public void seeNextEvents() {
        //TODO

    }

    public void searchUsingDate(int month, int day) {
        //TODO

    }

    public void searchUsingBudget(int budget) {
        //TODO

    }
}
