package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DriverTest {

    @Test
    void testGetNameReturnsDriverName() {
        Driver driver = new Driver("Ahmed", new Car());
        assertEquals("Ahmed", driver.getName());
    }

    @Test
    void testGetVehicleReturnsDriverVehicle() {
        Car car = new Car();
        Driver driver = new Driver("Ahmed", car);

        assertSame(car, driver.getVehicle());
        assertEquals("Car", driver.getVehicle().getType());
    }
}
