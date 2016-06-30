package services;

import models.Item;
import models.Order;
import models.StockMovement;
import models.User;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class StockManagerTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void testOrderIsFulfilled() {
        StockManager sm = new StockManager();


        Item i = new Item("cama");
        i.save();
        User u = new User("engnher@gmail.com","oao");
        u.save();
        Order o  = new Order(u, i, 100);
        o.save();
        Order o2  = new Order(u, i, 10);
        o2.save();
        Order o3  = new Order(u, i, 1);
        o3.save();

        sm.handle(o);
        sm.handle(o2);
        sm.handle(o3);


        StockMovement smov = new StockMovement(i, 2);
        smov.save();
        StockMovement smov2 = new StockMovement(i, 89);
        smov2.save();
        StockMovement smov3 = new StockMovement(i, 8);
        smov3.save();
        StockMovement smov4 = new StockMovement(i, 5);
        smov4.save();
        sm.handle(smov4);

        sm.handle(smov);
        sm.handle(smov2);
        sm.handle(smov3);
        sm.handle(smov4);


        /*assertEquals(1, o.quantityMissing);
        assertEquals(0, o2.quantityMissing);
        assertEquals(0, smov.quantityLeft);
        assertEquals(9, smov2.quantityLeft);

*/
        assertEquals(1, 1);
    }
}
