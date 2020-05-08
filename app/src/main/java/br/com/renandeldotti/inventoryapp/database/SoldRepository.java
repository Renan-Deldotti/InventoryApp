package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class SoldRepository {
    private SoldDao soldDao;
    private LiveData<List<Sold>> allSales;
    LiveData<List<QuantityAndPrice>> soldSince;

    public SoldRepository(Application application){
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        soldDao = database.soldDao();
        allSales = soldDao.getAllSales();
        soldSince = soldDao.getTotalSoldSince(new Date().getTime());
    }

    public LiveData<List<Sold>> getAllSales() {
        return allSales;
    }
    public void insert(Sold sold){new thisAsyncTask(soldDao).execute(sold);}

    public LiveData<List<QuantityAndPrice>> getSoldSince(long dateInMilli) {
        soldSince = soldDao.getTotalSoldSince(dateInMilli);
        return soldSince;
    }

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
