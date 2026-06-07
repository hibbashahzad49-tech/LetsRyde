package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CarTest {

    @Test
    void shouldCalculateFareForCarCorrectly() {
        Car car = new Car();

        assertEquals(0.0, car.calculateFare(0.0));
        assertEquals(200.0, car.calculateFare(1.0));
        assertEquals(1000.0, car.calculateFare(5.0));
    }

    @Test
    void shouldReturnCarType() {
        Car car = new Car();

        assertEquals("Car", car.getType());
    }
}
