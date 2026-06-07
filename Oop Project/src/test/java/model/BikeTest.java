package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BikeTest {

    @Test
    void shouldCalculateFareForBikeCorrectly() {
        Bike bike = new Bike();

        assertEquals(0.0, bike.calculateFare(0.0));
        assertEquals(150.0, bike.calculateFare(1.0));
        assertEquals(375.0, bike.calculateFare(2.5));
    }

    @Test
    void shouldReturnBikeType() {
        Bike bike = new Bike();

        assertEquals("Bike", bike.getType());
    }
}
