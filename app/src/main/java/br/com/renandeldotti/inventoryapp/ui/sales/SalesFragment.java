package br.com.renandeldotti.inventoryapp.ui.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.renandeldotti.inventoryapp.R;

public class SalesFragment extends Fragment {

    private SalesViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sales, container, false);
        return root;
    }
}
