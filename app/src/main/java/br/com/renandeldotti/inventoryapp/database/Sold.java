package br.com.renandeldotti.inventoryapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_products_sold")
public class Sold {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int product_id;

    private float sold_price;

    private int quantity_sold;

    // Constructor

    public Sold(int product_id, float sold_price, int quantity_sold) {
        this.product_id = product_id;
        this.sold_price = sold_price;
        this.quantity_sold = quantity_sold;
    }

    // Getters and  Setters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public float getSold_price() {
        return sold_price;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }
}
