package model;

public class Driver {
    private String name;
    private Vehicle vehicle;

    public Driver(String name, Vehicle vehicle) {
        this.name = name;
        this.vehicle = vehicle;
    }
    public String getName() {
        return name;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
}
