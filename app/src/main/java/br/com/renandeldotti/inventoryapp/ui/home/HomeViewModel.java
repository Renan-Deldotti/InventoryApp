package br.com.renandeldotti.inventoryapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsRepository;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.database.Sold;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private SoldRepository soldRepository;
    private ProductsRepository productsRepository;

    static final long ONE_DAY = 86400000;
    static final long ONE_WEEK = 604800000;
    static final long ONE_MONTH = Long.parseLong("2628000000");

    private LiveData<List<Products>> allProducts,productsSorted;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        soldRepository = new SoldRepository(application);
        productsRepository = new ProductsRepository(application);
        productsSorted = productsRepository.getProductsSortedByQuantity();
    }

    public LiveData<List<Products>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<Products>> getProductsSorted() {
        return productsSorted;
    }

    LiveData<List<QuantityAndPrice>> getSalesSince(long since) {
        long toSearch = new Date().getTime() - since;
        return soldRepository.getSoldSince(toSearch);
    }

}