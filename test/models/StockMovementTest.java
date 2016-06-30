package models;

import models.Item;
import models.StockMovement;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class StockMovementTest extends UnitTest {

    /*@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
*/
    @Test
    public void createAndRetrieveItem() {
        Item i = new Item("cama").save();

        new StockMovement(i, 10);
    }
}
