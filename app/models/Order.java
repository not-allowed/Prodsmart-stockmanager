package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import controllers.CRUD.Hidden;


@Entity
@Table(name = "purchase_order")
public class Order extends Model {
    @ManyToOne(cascade = CascadeType.ALL)
    public User user;
    @ManyToOne
    public Item item;

    public int quantity;

    @Hidden
    public int quantityMissing;

    @Hidden
    public Date creationDate;

    // Calculated field to show in CRUD list. Its value is calculated by it's getter on this class.
    // TODO put annotation @Transient on this field to avoid persisting it to DB.
    // Tried, and didn't work. Stops compiling.
    @Hidden
    public double completedPercentage;

    public Order(User user, Item item, int quantity) {
        this.user = user;
        this.item = item;

        this.setQuantity(quantity);
        this.creationDate = new Date();
    }

    public Order() {
        this.creationDate = new Date();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.quantityMissing = quantity;
        this.completedPercentage = 0;
    }

    public double getCompletedPercentage() {
        return ((double) (this.quantity - this.quantityMissing) / this.quantity) * 100;
    }

    /**
     * Updates order quantity missing with new stock quantity.
     * Quantity missing = quntity missing - new stock
     *
     * @param stockQuantity
     * @return int still missing quantity to fulfill this order
     */
    public int addItems(int stockQuantity) {
        quantityMissing -= stockQuantity;
        return quantityMissing;
    }

    public static Order getNextToBeFulfilledForItem(Item item) {
        List<Order> orders = Order.find("item_id = ? and quantityMissing > 0 order by creationDate DESC", item.id).fetch(1);

        return (orders.isEmpty()) ? null : orders.get(0);
    }

    public boolean isFulfilled() {
        return this.quantityMissing == 0;
    }
}