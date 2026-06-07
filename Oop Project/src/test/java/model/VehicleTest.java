package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class VehicleTest {

    @Test
    void testCarCalculateFareAndType() {
        Vehicle car = new Car();
        assertEquals(200.0, car.calculateFare(1.0));
        assertEquals("Car", car.getType());
    }

    @Test
    void testBikeCalculateFareAndType() {
        Vehicle bike = new Bike();
        assertEquals(150.0, bike.calculateFare(1.0));
        assertEquals("Bike", bike.getType());
    }
}
