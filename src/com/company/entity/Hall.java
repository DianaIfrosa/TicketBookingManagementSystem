package com.company.entity;

import java.util.Objects;

public class Hall {
    private int id;
    private String name;
    private int floor;
    private int seatsNumber;
    private boolean available = true;
    private int rows; // for the seats matrix
    private int columns; // for the seats matrix

    public Hall(){}
    public Hall(int id, String name, int floor, boolean available, int rows, int columns) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.rows = rows;
        this.columns = columns;
        this.seatsNumber = rows * columns;
    }

    public Hall(String name, int floor, boolean available, int rows, int columns) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.rows = rows;
        this.columns = columns;
        this.seatsNumber = rows * columns;
    }


    public int getId() { return id; }
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

    public void setId(int id) { this.id = id; }

    public String toString() {
        return "Hall name: " + name + "\n Floor: " + floor;
    }

    public void showDetails() {
        System.out.println("Id: " + id +
                "\nHall name: " + name +
                "\nAvailable: " + available +
                "\nSeats number: " + seatsNumber +
                "\nFloor: " + floor + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall)) return false;
        Hall hall = (Hall) o;
        return getId() == hall.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
