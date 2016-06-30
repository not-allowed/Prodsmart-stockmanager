package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {

    public String name;
    public String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return name + " - " + email;
    }

}