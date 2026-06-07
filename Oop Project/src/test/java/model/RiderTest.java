package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RiderTest {

    @Test
    void testGetName() {
        Rider rider = new Rider("Ayesha");
        assertEquals("Ayesha", rider.getName());
    }
}
