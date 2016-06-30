package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class StockOrderMovement extends Model {

    @ManyToOne
    public StockMovement stock;

    @ManyToOne
    public Order order;
    public int quantity;
    public Date creationDate;

    public StockOrderMovement(StockMovement s, Order o, int quantity) {
        this.stock = s;
        this.order = o;
        this.quantity = quantity;
        this.creationDate = new Date();
    }
}