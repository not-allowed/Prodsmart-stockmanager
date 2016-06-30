package models;

import controllers.CRUD;
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
    public int quantityLeft;

    @Hidden
    public Date creationDate;

    public StockMovement() {
        this.creationDate = new Date();
    }

    public StockMovement(Item item, int quantity) {
        this.item = item;
        setQuantity(quantity);
        this.creationDate = new Date();
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
        this.quantityLeft = quantity;
    }

    public static StockMovement getNextWithAvailableItems(Item i){
        List<StockMovement> stocks = StockMovement.find("item_id = ? and quantityLeft > 0 order by creationDate", i.id).fetch(1);
        return (stocks.isEmpty()) ? null : stocks.get(0);
    }

    /**
     * This method receives a requestedQuantity of items, and returns how much quantity
     * this stock can fulfill.
     *
     * Also remove provided stock from quantityLeft
     *
     * @param requestedQuantity
     * @return int
     */
    public int pickItems(int requestedQuantity){
        int nrOfItemsFromThisStock = quantityLeft;

        if(quantityLeft >= requestedQuantity){
            nrOfItemsFromThisStock = requestedQuantity;
        }

        quantityLeft -= nrOfItemsFromThisStock;
        return nrOfItemsFromThisStock;
    }

    public boolean isExhausted(){
        return !(this.quantityLeft > 0);
    }

}