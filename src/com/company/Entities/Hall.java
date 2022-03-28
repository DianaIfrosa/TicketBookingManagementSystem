package com.company.Entities;

import java.util.*;

public class Hall {
    private String name; //identifier
    private int floor;
    private int seatsNumber;
    private boolean available = true; //a hall will be marked as unavailable if it is not suitable for an event at that moment
    private int rows, columns; //for the seats matrix

    public Hall(){}
    public Hall(String name, int floor, boolean available, int rows, int columns) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.rows = rows;
        this.columns = columns;

        this.seatsNumber = rows * columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public String toString() {
        return "Hall name: " + name + "\n Floor: " + floor;
    }
}
