package model;

public class Car extends Vehicle {

    public double calculateFare(double distance) {
        return distance * 200;
    }
    public String getType() {
        return "Car";
    }
}
