package br.com.renandeldotti.inventoryapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.database.SoldRepository;

public class HomeViewModel extends AndroidViewModel {

    private SoldRepository soldRepository;

    public static final long ONE_DAY = 86400000;
    public static final long ONE_WEEK = 604800000;
    public static final long ONE_MONTH = Long.parseLong("2628000000");

    public HomeViewModel(@NonNull Application application) {
        super(application);
        soldRepository = new SoldRepository(application);
    }

    public LiveData<List<QuantityAndPrice>> getItemsSold(long sinceDate) {
        return soldRepository.getSoldSince(sinceDate);
    }
}