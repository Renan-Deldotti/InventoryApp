package br.com.renandeldotti.inventoryapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface ProductsDao {

    @Insert
    void insert(Products products);

    @Update
    void update(Products products);

    @Delete
    void delete(Products products);

    @Query("SELECT * FROM table_products ORDER BY id")
    LiveData<List<Products>> getAllProducts();

    @Query("SELECT * FROM table_products ORDER BY quantity_sold")
    LiveData<List<Products>> getMostSold();

    @Query("SELECT * FROM table_products ORDER BY quantity_sold DESC")
    LiveData<List<Products>> getLessSold();

    @Query("DELETE FROM table_products")
    void deleteAllProducts();

    @Query("DELETE FROM table_products WHERE id = :product_id")
    void deleteSingleItem(int product_id);

    @Query("SELECT * FROM table_products ORDER BY quantity_sold")
    LiveData<List<Products>> getAllProductsSortedByQuantity();
}
