package br.com.renandeldotti.inventoryapp.ui.stock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.com.renandeldotti.inventoryapp.R;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stockViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StockViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        return root;
    }
}
