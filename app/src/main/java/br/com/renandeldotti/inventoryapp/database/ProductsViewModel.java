package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

class ProductsViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private LiveData<List<Products>> products;
    private LiveData<List<Sold>> soldProducts;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
        products = productsRepository.getAllProducts();
        soldProducts = productsRepository.getAllSales();
    }

    void insert(Products products){productsRepository.insert(products);}
    void update(Products products){productsRepository.update(products);}
    void delete(Products products){productsRepository.delete(products);}
    void deleteAll(){productsRepository.deleteAllData();}
    LiveData<List<Products>> getProducts() {return products;}
    LiveData<List<Sold>> getSoldProducts(){return soldProducts;}
}
