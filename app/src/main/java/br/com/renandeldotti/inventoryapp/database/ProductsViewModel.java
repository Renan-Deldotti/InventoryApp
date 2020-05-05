package br.com.renandeldotti.inventoryapp.database;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private SoldRepository soldRepository;
    private LiveData<List<Products>> products;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
        soldRepository = new SoldRepository(application);
        products = productsRepository.getAllProducts();
    }

    public void insert(Products products){productsRepository.insert(products);}
    public void update(Products products){productsRepository.update(products);}
    public void delete(Products products){productsRepository.delete(products);}
    void deleteAll(){productsRepository.deleteAllData();}
    public void deleteSingleItem(int singleItemId){productsRepository.deleteSingleItem(singleItemId);}
    LiveData<List<Products>> getProducts() {return products;}

    public void insertNewSale(Sold sold){soldRepository.insert(sold);}
}
