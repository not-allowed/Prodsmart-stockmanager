package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Date;

import controllers.CRUD.Hidden;

@Entity
public class StockMovement extends Model {

    @ManyToOne
    public Item item;
    public int quantity;

    @Hidden
    public int quantityRemaining;

    @Hidden
    public Date creationDate;

    public StockMovement() {
        this.creationDate = new Date();
    }

    public StockMovement(Item item, int quantity) {
        this.item = item;
        this.setQuantity(quantity);
        this.creationDate = new Date();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        quantityRemaining = quantity;
    }

    public static StockMovement getNextWithAvailableItems(Item i) {
        List<StockMovement> stocks = StockMovement.find("item_id = ? and quantityRemaining > 0 order by creationDate", i.id).fetch(1);
        return (stocks.isEmpty()) ? null : stocks.get(0);
    }

    /**
     * This method receives a requestedQuantity of items, and returns how much quantity
     * this stock can fulfill.
     * <p>
     * Also remove provided stock from quantityRemaining
     *
     * @param requestedQuantity
     * @return int
     */
    public int pickItems(int requestedQuantity) {
        int nrOfItemsFromThisStock = quantityRemaining;

        if (quantityRemaining >= requestedQuantity) {
            nrOfItemsFromThisStock = requestedQuantity;
        }

        quantityRemaining -= nrOfItemsFromThisStock;
        return nrOfItemsFromThisStock;
    }

    public boolean isExhausted() {
        return !(this.quantityRemaining > 0);
    }
}