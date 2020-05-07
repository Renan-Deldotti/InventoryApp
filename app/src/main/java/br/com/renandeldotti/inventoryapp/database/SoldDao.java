package br.com.renandeldotti.inventoryapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SoldDao {

    @Insert
    void insert(Sold sold);

    @Update
    void update(Sold sold);

    @Delete
    void delete(Sold sold);

    @Query("SELECT * FROM table_products_sold ORDER BY id DESC")
    LiveData<List<Sold>> getAllSales();

    @Query("SELECT sold_price,quantity_sold FROM table_products_sold WHERE date_added > :dateToSearch")
    LiveData<List<QuantityAndPrice>> getTotalSoldSince(long dateToSearch);
}
