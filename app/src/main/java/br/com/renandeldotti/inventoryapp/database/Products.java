package br.com.renandeldotti.inventoryapp.database;

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

    private float price;
}
