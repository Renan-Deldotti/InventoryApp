package br.com.renandeldotti.inventoryapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_products_sold")
public class Sold {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String product_name;

    private float sold_price;

    private int quantity_sold;

    private String date_added;

    // Constructor

    public Sold(String product_name, float sold_price, int quantity_sold, String date_added) {
        this.product_name = product_name;
        this.sold_price = sold_price;
        this.quantity_sold = quantity_sold;
        this.date_added = date_added;
    }

    // Getters and  Setters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public float getSold_price() {
        return sold_price;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public String getDate_added() {return date_added;}
}
