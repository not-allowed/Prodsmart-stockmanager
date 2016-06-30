package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Date;

@Entity
public class StockMovement extends Model {

    @ManyToOne
    public Item item;

    public int quantity;
    public int quantityLeft;
    public Date creationDate;

    public StockMovement(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.quantityLeft = quantity;
        this.creationDate = new Date();
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