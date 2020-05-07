package br.com.renandeldotti.inventoryapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {

    private long oneDayInMilliseconds = 86400000;
    private long weekInMilliseconds = 604800000;
    private long monthInMilliseconds = Long.parseLong("2628000000");

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
}