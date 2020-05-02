package br.com.renandeldotti.inventoryapp.ui.sales;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.renandeldotti.inventoryapp.database.ProductsRepository;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class SalesViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private LiveData<List<Sold>> allSales;

    public SalesViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
        allSales = productsRepository.getAllSales();
    }

    public LiveData<List<Sold>> getAllSales() {
        return allSales;
    }
}