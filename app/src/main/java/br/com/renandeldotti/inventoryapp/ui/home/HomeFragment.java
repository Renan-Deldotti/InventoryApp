package br.com.renandeldotti.inventoryapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.ui.sales.SalesViewModel;

public class HomeFragment extends Fragment {

    private View tvRitmoVendas;
    private int isTvRitmoVendasVisible;
    private HomeViewModel homeViewModel;
    private long totalSold = 0;
    private TextView homeSales,weekSales,monthSales;
    private String totalString = "";

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);

        tvRitmoVendas = root.findViewById(R.id.home_rateSales_hidden);
        isTvRitmoVendasVisible = tvRitmoVendas.getVisibility();

        homeSales = root.findViewById(R.id.home_todaySales_value);
        weekSales = root.findViewById(R.id.home_weekSales_value);
        monthSales = root.findViewById(R.id.home_monthSales_value);

        try {
            if (homeViewModel.getItemsSold(HomeViewModel.ONE_DAY) != null){
                homeViewModel.getItemsSold(HomeViewModel.ONE_DAY).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                    @Override
                    public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                        for (int i = 0; i < quantityAndPrices.size();i++){
                            totalSold += quantityAndPrices.get(i).getQuantity_sold();
                        }
                        homeSales.setText(String.valueOf(totalSold));
                        weekSales.setText(String.valueOf(totalSold));
                        monthSales.setText(String.valueOf(totalSold));
                    }
                });
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            if (getView()!=null)
            Snackbar.make(getView(),"Error", BaseTransientBottomBar.LENGTH_SHORT).show();
            Log.e(HomeFragment.class.getSimpleName(),"Error: " + e);
        }

        root.findViewById(R.id.ritmo_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTvRitmoVendasVisible == View.VISIBLE){
                    tvRitmoVendas.setVisibility(View.GONE);
                    isTvRitmoVendasVisible = View.GONE;
                }else {
                    tvRitmoVendas.setVisibility(View.VISIBLE);
                    isTvRitmoVendasVisible = View.VISIBLE;
                }
            }
        });


        root.findViewById(R.id.aumento_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.queda_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.total_vendas).setOnClickListener(clickListener);


        return root;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Todo: Criar tela...", Toast.LENGTH_SHORT).show();
        }
    };
}
