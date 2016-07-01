package models;

import models.Item;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class ItemTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void createAndRetrieveItem() {
        new Item("cama").save();

        Item item = Item.find("byName", "cama").first();

        // Test
        assertNotNull(item);
        assertEquals("cama", item.name);
    }
}
