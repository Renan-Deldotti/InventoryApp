package br.com.renandeldotti.inventoryapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Date;
import java.util.List;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsRepository;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class HomeViewModel extends AndroidViewModel {

    private SoldRepository soldRepository;
    private ProductsRepository productsRepository;

    static final long ONE_DAY = 86400000;
    static final long ONE_WEEK = 604800000;
    static final long ONE_MONTH = Long.parseLong("2628000000");

    private String quantityTotal = "";
    private LiveData<List<Products>> allProducts;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        soldRepository = new SoldRepository(application);
        productsRepository = new ProductsRepository(application);
    }

    public LiveData<List<Products>> getAllProducts() {
        return allProducts;
    }

    String getSales(long since, LifecycleOwner owner){
        long toSearch = new Date().getTime() - since;
        try {
            if (soldRepository.getSoldSince(toSearch) != null){
                soldRepository.getSoldSince(toSearch).observe(owner, new Observer<List<QuantityAndPrice>>() {
                    @Override
                    public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                        long longSold = 0;
                        for (int i =0; i< quantityAndPrices.size();i++){
                            longSold += quantityAndPrices.get(i).getQuantity_sold();
                        }
                        quantityTotal = String.valueOf(longSold);
                    }
                });
            }else {
                quantityTotal = "0";
            }
        }catch (Exception e){
            Log.e(HomeViewModel.class.getSimpleName(),"Error:\t"+e);
            quantityTotal = "Error";
        }
        return quantityTotal;
    }

    String[] mostSoldProducts(LifecycleOwner owner){
        final String[] mostSold = new String[2];
        try {
            if (productsRepository.getProductsSortedByQuantity() != null){
                productsRepository.getProductsSortedByQuantity().observe(owner, new Observer<List<Products>>() {
                    @Override
                    public void onChanged(List<Products> products) {
                        if (products.size() >= 2){
                            mostSold[0] = "Product "+products.get(0).getProduct_name() + "number of sales: "+products.get(0).getQuantity_sold();
                            mostSold[1] = "Product "+products.get(1).getProduct_name() + "number of sales: "+products.get(1).getQuantity_sold();
                        }else {
                            if (products.size() == 1){
                                mostSold[0] = "Product "+products.get(0).getProduct_name() + "number of sales: "+products.get(0).getQuantity_sold();
                                mostSold[1] = "";
                            }else{
                                mostSold[0] = "";
                                mostSold[1] = "";
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(HomeViewModel.class.getSimpleName(),"Error\t"+e);
        }
        return mostSold;
    }
}