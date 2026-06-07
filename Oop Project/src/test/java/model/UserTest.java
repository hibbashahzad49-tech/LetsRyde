package model;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserTest {

    @Test
    void testAbstractUserNameIsStored() {
        var user = new User("Ali") {
            public String getNamePublic() {
                return name;
            }
        };

        assertEquals("Ali", user.getNamePublic());
    }
}
