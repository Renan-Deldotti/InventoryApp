package br.com.renandeldotti.inventoryapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Products.class}, version = 1)
abstract class InventoryDatabase extends RoomDatabase {

    private static InventoryDatabase instance;

    abstract ProductsDao productsDao();

    static synchronized InventoryDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),InventoryDatabase.class,"inventory_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
