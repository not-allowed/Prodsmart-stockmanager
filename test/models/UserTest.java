package models;

import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {

  /*  @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }*/

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("not", "engenheironot@gmail.com").save();

        // Retrieve the user with e-mail address bob@gmail.com
        User not = User.find("byEmail", "engenheironot@gmail.com").first();

        // Test
        assertNotNull(not);
        assertEquals("not", not.name);
    }

}
