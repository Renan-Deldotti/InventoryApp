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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private View tvRitmoVendas;
    private int isTvRitmoVendasVisible;
    private HomeViewModel homeViewModel;
    private TextView todaySales,weekSales,monthSales;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
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
                final View parentView = root.findViewById(R.id.home_salesRise_hidden);

                if (homeViewModel.getProductsSorted() != null){
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            TextView salesRiseFirstName = root.findViewById(R.id.home_salesRise_first_name);
                            TextView salesRiseFirstValue = root.findViewById(R.id.home_salesRise_first_value);
                            TextView salesRiseSecondName = root.findViewById(R.id.home_salesRise_second_name);
                            TextView salesRiseSecondValue = root.findViewById(R.id.home_salesRise_second_value);
                            if (parentView.getVisibility() == View.GONE || parentView.getVisibility() == View.INVISIBLE){
                                if(products.size() == 1){
                                    salesRiseFirstName.setText(products.get(0).getProduct_name());
                                    salesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                }else if(products.size() > 1){
                                    salesRiseFirstName.setText(products.get(0).getProduct_name());
                                    salesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                    salesRiseSecondName.setText(products.get(1).getProduct_name());
                                    salesRiseSecondValue.setText(String.valueOf(products.get(1).getQuantity_sold()));
                                }
                                parentView.setVisibility(View.VISIBLE);
                            }else {
                                parentView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                /*if (view.getVisibility() == View.GONE) {
                    String[][] mostSold = homeViewModel.getMostSoldProducts(getViewLifecycleOwner());
                    TextView salesRiseFirstName = root.findViewById(R.id.home_salesRise_first_name);
                    TextView salesRiseFirstValue = root.findViewById(R.id.home_salesRise_first_value);
                    TextView salesRiseSecondName = root.findViewById(R.id.home_salesRise_second_name);
                    TextView salesRiseSecondValue = root.findViewById(R.id.home_salesRise_second_value);

                    if (mostSold != null && mostSold.length > 1) {
                        salesRiseFirstName.setText(mostSold[0][0]);
                        salesRiseFirstValue.setText(mostSold[0][1]);
                        salesRiseSecondName.setText(mostSold[1][0]);
                        salesRiseSecondValue.setText(mostSold[1][1]);
                    }
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }*/

            }
        });


        root.findViewById(R.id.queda_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View parentView = root.findViewById(R.id.home_salesDrop_hidden);
                if(homeViewModel.getProductsSorted() != null){
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            int arrSize = products.size();
                            TextView salesDropFirstName = root.findViewById(R.id.home_salesDrop_first_name);
                            TextView salesDropFirstValue = root.findViewById(R.id.home_salesDrop_first_value);
                            TextView salesDropSecondName = root.findViewById(R.id.home_salesDrop_second_name);
                            TextView salesDropSecondValue = root.findViewById(R.id.home_salesDrop_second_value);
                            if (parentView.getVisibility() == View.GONE){
                                if (arrSize > 1){
                                    salesDropFirstName.setText(products.get(arrSize-1).getProduct_name());
                                    salesDropFirstValue.setText(String.valueOf(products.get(arrSize-1).getQuantity_sold()));
                                    salesDropSecondName.setText(products.get(arrSize-2).getProduct_name());
                                    salesDropSecondValue.setText(String.valueOf(products.get(arrSize-2).getQuantity_sold()));
                                }else if(arrSize == 1){
                                    salesDropFirstName.setText(products.get(arrSize-1).getProduct_name());
                                    salesDropFirstValue.setText(String.valueOf(products.get(arrSize-1).getQuantity_sold()));
                                }
                                parentView.setVisibility(View.VISIBLE);
                            }else {
                                parentView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });


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
