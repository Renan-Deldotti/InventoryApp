package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;

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
}
