package services;

import models.Order;
import models.StockMovement;
import models.StockOrderMovement;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;

public class StockManager {

    public StockManager(){
        //por aqui o mail?
    }

    public static int handle(Order order){
        StockMovement stock;
        while(!order.isFulfilled()
                && (stock = StockMovement.getNextWithAvailableItems(order.item)) != null){
            handleStockOrderMovement(stock, order);
        }

        if(order.isFulfilled()) {
            sendMail(order);
        }

        return order.quantityMissing;
    }

    public static int handle(StockMovement stock){
        Order order;
        while(!stock.isExhausted()
                && (order = Order.getNextToBeFulfilledForItem(stock.item)) != null){
            handleStockOrderMovement(stock, order);

            if(order.isFulfilled()) {
                sendMail(order);
            }
        }

        return stock.quantityRemaining;
    }

    protected static void handleStockOrderMovement(StockMovement stock, Order order){
        //pick as many items as possible from this stock
        int pickedItems = stock.pickItems(order.quantityMissing);
        order.addItems(pickedItems);

        stock.save();
        order.save();

        //create an entry to log this movement from stock to order
        StockOrderMovement som = new StockOrderMovement(stock, order, pickedItems);
        som.save();
    }

    public static void sendMail(Order order){
        try {
            SimpleEmail email = new SimpleEmail();
            email.setFrom("engenheironot@gmail.com");
            email.addTo(order.user.email);
            email.setSubject("Your order has been fulfilled.");
            email.setMsg("Your order of " + order.quantity + " " + order.item.name + " has been fulfilled.");
            Mail.send(email);
        }
        catch (EmailException e) {
                e.printStackTrace();
            }
    }
}
