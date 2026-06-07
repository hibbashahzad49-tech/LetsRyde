package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RideTest {

    @Test
    void testFareForCar() {
        Rider rider = new Rider("Ali");
        Driver driver = new Driver("Ahmed", new Car());
        Ride ride = new Ride(rider, driver, 10.0);

        assertEquals(500.0, ride.getFare());
    }

    @Test
    void testFareForBike() {
        Rider rider = new Rider("Ali");
        Driver driver = new Driver("Ahmed", new Bike());
        Ride ride = new Ride(rider, driver, 10.0);

        assertEquals(300.0, ride.getFare());
    }
}
