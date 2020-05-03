package br.com.renandeldotti.inventoryapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Products.class,Sold.class}, version = 1, exportSchema = false)
abstract class InventoryDatabase extends RoomDatabase {

    private static InventoryDatabase instance;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    abstract ProductsDao productsDao();
    abstract SoldDao soldDao();

    static synchronized InventoryDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),InventoryDatabase.class,"inventory_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private ProductsDao noteDao;
        private SoldDao soldDao;

        private PopulateDbAsyncTask(@NotNull InventoryDatabase db){
            noteDao = db.productsDao();
            soldDao = db.soldDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Products("Produto 1","Descrição 1",1,15));
            noteDao.insert(new Products("Produto 2","Descrição 1",1,15));
            noteDao.insert(new Products("Produto 3","Descrição 1",1,15));
            noteDao.insert(new Products("Produto 4","Descrição 1",1,15));
            noteDao.insert(new Products("Produto 5","Descrição 1",1,15));
            soldDao.insert(new Sold("Produto um",(float) 1.5,15));
            return null;
        }
    }
}
