package br.com.renandeldotti.inventoryapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Products.class}, version = 1, exportSchema = false)
abstract class InventoryDatabase extends RoomDatabase {

    private static InventoryDatabase instance;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
