package br.com.renandeldotti.inventoryapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_stock_removal")
class OutProducts {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int qnt_sold;

    // Foreignkey id
    private int product_id;
}
