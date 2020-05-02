package br.com.renandeldotti.inventoryapp.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "table_products")
class Products {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String product_name;

    private String product_description;

    private int product_quantity;

    private int quantity_sold;

    private float price;

    public Products(String product_name, String product_description, int product_quantity, float price) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_quantity = product_quantity;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public float getPrice() {
        return price;
    }
}
