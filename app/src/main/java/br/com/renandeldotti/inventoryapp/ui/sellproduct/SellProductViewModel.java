package br.com.renandeldotti.inventoryapp.ui.sellproduct;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsRepository;
import br.com.renandeldotti.inventoryapp.database.Sold;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class SellProductViewModel extends AndroidViewModel {
    private SoldRepository soldRepository;
    private ProductsRepository productsRepository;

    public SellProductViewModel(@NonNull Application application) {
        super(application);
        soldRepository = new SoldRepository(application);
        productsRepository = new ProductsRepository(application);
    }

    void makeNewSale(Products products, Sold sold){
        soldRepository.insert(sold);
        productsRepository.update(products);
    }
}
