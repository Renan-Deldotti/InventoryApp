package br.com.renandeldotti.inventoryapp.ui.sales;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsRepository;
import br.com.renandeldotti.inventoryapp.database.Sold;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class SalesViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private SoldRepository soldRepository;
    private LiveData<List<Sold>> allSales;
    private LiveData<List<Products>> allProducts;

    public SalesViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
        soldRepository = new SoldRepository(application);
        allSales = soldRepository.getAllSales();
        allProducts = productsRepository.getAllProducts();
    }

    public LiveData<List<Sold>> getAllSales() {
        return allSales;
    }
    public LiveData<List<Products>> getAllProducts(){return allProducts;}
}