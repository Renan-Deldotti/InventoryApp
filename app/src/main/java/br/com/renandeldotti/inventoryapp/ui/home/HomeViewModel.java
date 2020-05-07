package br.com.renandeldotti.inventoryapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class HomeViewModel extends AndroidViewModel {

    private SoldRepository soldRepository;

    static final long ONE_DAY = 86400000;
    static final long ONE_WEEK = 604800000;
    static final long ONE_MONTH = Long.parseLong("2628000000");

    private String quantityTotal = "";

    public HomeViewModel(@NonNull Application application) {
        super(application);
        soldRepository = new SoldRepository(application);
    }

    LiveData<List<QuantityAndPrice>> getItemsSold(long sinceDate) {
        return soldRepository.getSoldSince(sinceDate);
    }

    String getSales(long since, LifecycleOwner owner){
        try {
            if (soldRepository.getSoldSince(since) != null){
                soldRepository.getSoldSince(since).observe(owner, new Observer<List<QuantityAndPrice>>() {
                    @Override
                    public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                        long longSolds = 0;
                        for (int i =0; i< quantityAndPrices.size();i++){
                            longSolds += quantityAndPrices.get(i).getQuantity_sold();
                        }
                        quantityTotal = String.valueOf(longSolds);
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

}