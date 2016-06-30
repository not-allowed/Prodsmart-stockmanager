package models;

import play.db.jpa.Model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="purchase_order")
public class Order extends Model {

    @ManyToOne(cascade=CascadeType.ALL)
    public User user;
    @ManyToOne
    public Item item;
    public int quantity;
    public int quantityMissing;
    public Date creationDate;

    public Order(User user, Item item, int quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.quantityMissing = quantity;

        this.creationDate = new Date();
    }

    /**
     * Updates order quantity missing with new stock quantity.
     * Quantity missing = quntity missing - new stock
     *
     * @param stockQuantity
     * @return int still missing quantity to fulfill this order
     */
    public int addItems(int stockQuantity){
        quantityMissing -= stockQuantity;
        return quantityMissing;
    }

    public static Order getNextToBeFulfilledForItem(Item i){
        List<Order> orders = Order.find("item_id = ? and quantityMissing > 0 order by creationDate DESC", i.id).fetch(1);

        return (orders.isEmpty()) ? null : orders.get(0);
    }

    public double completedPercentage;
    public double getCompletedPercentage(){
        return ((double)(this.quantity - this.quantityMissing) / this.quantity) * 100;
    }
}