package services;

import models.Order;
import models.Item;
import models.StockMovement;
import models.StockOrderMovement;

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
            //Send mail
        }

        return order.quantityMissing;
    }

    public static int handle(StockMovement stock){
        Order order;
        while(!stock.isExhausted()
                && (order = Order.getNextToBeFulfilledForItem(stock.item)) != null){
            handleStockOrderMovement(stock, order);

            if(order.isFulfilled()) {
                //Send mail
            }
        }

        return stock.quantityLeft;
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
}
