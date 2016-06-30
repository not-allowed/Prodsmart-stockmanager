package services;

import models.Order;
import models.Item;
import models.StockMovement;
import models.StockOrderMovement;

public class StockManager {

    public StockManager(){
        //por aqui o mail?
    }

    public int handle(Order order){
        StockMovement stock;
        while(order.quantityMissing > 0
                && (stock = StockMovement.getNextWithAvailableItems(order.item)) != null){
            addStockOrderMovement(stock, order);
        }
        return order.quantityMissing;
    }

    public int handle(StockMovement stock){
        Order order;
        while(stock.quantityLeft > 0 
                && (order = Order.getNextToBeFulfilledForItem(stock.item)) != null){
            createStockOrderMovement(stock, order);
        }
        return stock.quantity;
    }

    protected void createStockOrderMovement(StockMovement stock, Order order){
        int pickedItems = stock.pickItems(order.quantityMissing);
        order.addItems(pickedItems);

        stock.save();
        order.save();

        StockOrderMovement som = new StockOrderMovement(stock, order, pickedItems);
        som.save();
    }
}
