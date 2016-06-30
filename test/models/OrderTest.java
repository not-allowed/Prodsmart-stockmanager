package models;

import models.Item;
import models.Order;
import models.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class OrderTest extends UnitTest {

   /* @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
*/
    @Test
    public void createAndRetrieveOrder() {
        User user = new User("not", "mail@mail.com").save();
        Item item1 = new Item("cama").save();
        Item item2 = new Item("banco").save();
        new Order(user, item1, 10).save();
        new Order(user, item2, 1).save();
        new Order(user, item2, 30).save();


        List<Order> orders = Order.findAll();

        // Test
        assertNotNull(orders);

        Order firstOrder = orders.get(0);
        System.out.println(firstOrder);
    }

}
