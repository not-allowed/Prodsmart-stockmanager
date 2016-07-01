package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.*;

@Entity
public class Item extends Model {

    public String name;

    public Item(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}