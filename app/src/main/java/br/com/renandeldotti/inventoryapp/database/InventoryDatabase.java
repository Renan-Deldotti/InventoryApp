package br.com.renandeldotti.inventoryapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Products.class,OutProducts.class}, version = 1)
abstract class InventoryDatabase extends RoomDatabase {
}
