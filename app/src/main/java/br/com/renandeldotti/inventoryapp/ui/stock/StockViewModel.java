package br.com.renandeldotti.inventoryapp.ui.stock;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsRepository;

public class StockViewModel extends ViewModel {

    private ProductsRepository productsRepository;
    private LiveData<List<Products>> products;

    public StockViewModel(){}

    public StockViewModel(@NonNull Application application) {
        productsRepository = new ProductsRepository(application);
        products = productsRepository.getAllProducts();
    }

    void insert(Products products){productsRepository.insert(products);}
    void update(Products products){productsRepository.update(products);}
    void delete(Products products){productsRepository.delete(products);}
    void deleteAll(){productsRepository.deleteAllData();}
    LiveData<List<Products>> getProducts() {return products;}
}