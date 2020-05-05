package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SoldRepository {
    private SoldDao soldDao;
    private LiveData<List<Sold>> allSales;

    public SoldRepository(Application application){
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        soldDao = database.soldDao();
        allSales = soldDao.getAllSales();
    }

    public LiveData<List<Sold>> getAllSales() {
        return allSales;
    }
    public void insert(Sold sold){new thisAsyncTask(soldDao).execute(sold);}

    private static class thisAsyncTask extends AsyncTask<Sold,Void,Void>{
        private SoldDao soldDao;
        thisAsyncTask(SoldDao soldDao){
            this.soldDao = soldDao;
        }
        @Override
        protected Void doInBackground(Sold... solds) {
            soldDao.insert(solds[0]);
            return null;
        }
    }
}
