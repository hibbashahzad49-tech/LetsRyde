package model;

public class Bike extends Vehicle {

    public double calculateFare(double distance) {
        return distance * 150;
    }
    public String getType() {
        return "Bike";
    }
}
