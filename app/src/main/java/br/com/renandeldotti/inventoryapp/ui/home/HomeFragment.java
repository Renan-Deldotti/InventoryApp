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
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.List;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private View tvRitmoVendas;
    private int isTvRitmoVendasVisible;
    private HomeViewModel homeViewModel;
    private TextView todaySales,weekSales,monthSales;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);

        tvRitmoVendas = root.findViewById(R.id.home_rateSales_hidden);
        isTvRitmoVendasVisible = tvRitmoVendas.getVisibility();

        todaySales = root.findViewById(R.id.home_todaySales_value);
        weekSales = root.findViewById(R.id.home_weekSales_value);
        monthSales = root.findViewById(R.id.home_monthSales_value);

        root.findViewById(R.id.ritmo_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTvRitmoVendasVisible == View.VISIBLE){
                    tvRitmoVendas.setVisibility(View.GONE);
                    isTvRitmoVendasVisible = View.GONE;
                }else {
                    todaySales.setText(homeViewModel.getSales(HomeViewModel.ONE_DAY,getViewLifecycleOwner()));
                    weekSales.setText(homeViewModel.getSales(HomeViewModel.ONE_WEEK,getViewLifecycleOwner()));
                    monthSales.setText(homeViewModel.getSales(HomeViewModel.ONE_MONTH,getViewLifecycleOwner()));

                    tvRitmoVendas.setVisibility(View.VISIBLE);
                    isTvRitmoVendasVisible = View.VISIBLE;

                    Log.e("Test :: Today sales", todaySales.getText().toString());
                    Log.e("Test :: Week sales",weekSales.getText().toString());
                    Log.e("Test :: Month sales",monthSales.getText().toString());
                }
            }
        });


        root.findViewById(R.id.aumento_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mostSold = homeViewModel.getMostSoldProducts(getViewLifecycleOwner());
                for ( String s : mostSold){
                    Log.e(TAG,"Value of s: "+s);
                }
            }
        });


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
