package controllers;

import models.Item;
import models.Order;
import models.StockMovement;
import models.User;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import services.StockManager;

import java.util.List;

public class Orders extends CRUD {


    public static void add() {
        Item i = Item.findById(1L);
        StockMovement stock = StockMovement.getNextWithAvailableItems(i);
        System.out.println(stock);
        Order order = Order.getNextToBeFulfilledForItem(i);
        System.out.println(order);

      /*  Order o = new Order(users.get(0), i, 3);
        o.save();

        orderCreated(o);*/
        //render();
    }

    public static void orderCreated(Order order) {
        StockManager sm = new StockManager();
        int total = sm.handle(order);
        render();
    }
}
