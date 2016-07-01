package services;

import models.Order;
import models.StockMovement;
import models.StockOrderMovement;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;

public class StockManager {

    // TODO
    // Both these handle methods (for Order and StockMovement) are very similar.
    // Maybe could be moved into a higher level of abstraction.
    // Both Order and StockMovement would implement an interface with methods
    // isFinished and getNext.
    // Decided to keep it like this to ease readability.
    public static int handle(Order order) {
        StockMovement stock;
        while (!order.isFulfilled()
                && (stock = StockMovement.getNextWithAvailableItems(order.item)) != null) {
            handleStockOrderMovement(stock, order);
        }

        if (order.isFulfilled()) {
            sendMail(order);
        }
        return order.quantityMissing;
    }

    public static int handle(StockMovement stock) {
        Order order;
        while (!stock.isExhausted()
                && (order = Order.getNextToBeFulfilledForItem(stock.item)) != null) {
            handleStockOrderMovement(stock, order);

            if (order.isFulfilled()) {
                sendMail(order);
            }
        }
        return stock.quantityRemaining;
    }

    /**
     * Used to move stock from StockMovement to Order.
     * Then calls trace to maintain log of transactions.
     * Decided to keep two methods, one to handle stock moves and one to trace,
     * respecting the Single Responsibility Principle
     *
     * @param stock
     * @param order
     */
    protected static void handleStockOrderMovement(StockMovement stock, Order order){
        //pick as many items as possible from this stock
        int pickedItems = stock.pickItems(order.quantityMissing);
        order.addItems(pickedItems);

        stock.save();
        order.save();

        traceStockOrderMovement(stock, order, pickedItems);
    }

    /**
     * Create an entry to log the movement from stock to order.
     * Item moved can be accessed via order or stockmovement.
     * @param stock
     * @param order
     * @param quantityToTrace
     */
    protected static void traceStockOrderMovement(StockMovement stock, Order order, int quantityToTrace) {
        StockOrderMovement som = new StockOrderMovement(stock, order, quantityToTrace);
        som.save();
    }

    private static final String fromEmail = "example@gmail.com";

    public static void sendMail(Order order) {
        try {
            String message = String.format("Your order of %d %s has been fulfilled.", order.quantity, order.item.name);

            Email email = new SimpleEmail();
            email.setFrom(fromEmail);
            email.addTo(order.user.email);
            email.setSubject("Your order has been fulfilled.");
            email.setMsg(message);
            Mail.send(email);
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
