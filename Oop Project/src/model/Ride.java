package model;

public class Ride {
    private Rider rider;
    private Driver driver;
    private double distance;
    private String status;

    public Ride(Rider rider, Driver driver, double distance) {
        this.rider = rider;
        this.driver = driver;
        this.distance = distance;
        this.status = "Pending";
    }

    public double getFare() {
        String vehicleType = driver.getVehicle().getType();
        double ratePerKm = 0;
        if (vehicleType.equalsIgnoreCase("Car")) {
            ratePerKm = 50;
        } else if (vehicleType.equalsIgnoreCase("Bike")) {
            ratePerKm = 30;
        }
        return distance * ratePerKm;
    }

    public String getDetails() {
        return rider.getName() + "," +
                driver.getName() + "," +
                driver.getVehicle().getType() + "," +
                distance + "," +
                getFare() + "," +
                status;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public double getDistance() { return distance; }
}