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

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);

        root.findViewById(R.id.ritmo_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tvRitmoVendas = root.findViewById(R.id.home_rateSales_hidden);

                final TextView todaySales = root.findViewById(R.id.home_todaySales_value);
                final TextView weekSales = root.findViewById(R.id.home_weekSales_value);
                final TextView monthSales = root.findViewById(R.id.home_monthSales_value);

                if (tvRitmoVendas.getVisibility() == View.VISIBLE) {
                    tvRitmoVendas.setVisibility(View.GONE);
                } else {
                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_DAY) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_DAY).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                long soldQuantity = 0;
                                for (QuantityAndPrice qp : quantityAndPrices) {
                                    soldQuantity += qp.getQuantity_sold();
                                }
                                todaySales.setText(String.valueOf(soldQuantity));
                            }
                        });
                    }
                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                long soldQuantity = 0;
                                for (QuantityAndPrice qp : quantityAndPrices) {
                                    soldQuantity += qp.getQuantity_sold();
                                }
                                weekSales.setText(String.valueOf(soldQuantity));
                            }
                        });
                    }
                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                long soldQuantity = 0;
                                for (QuantityAndPrice qp : quantityAndPrices) {
                                    soldQuantity += qp.getQuantity_sold();
                                }
                                monthSales.setText(String.valueOf(soldQuantity));
                            }
                        });
                    }
                    tvRitmoVendas.setVisibility(View.VISIBLE);
                }
            }
        });


        root.findViewById(R.id.aumento_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View parentView = root.findViewById(R.id.home_salesRise_hidden);

                if (homeViewModel.getProductsSorted() != null) {
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            TextView salesRiseFirstName = root.findViewById(R.id.home_salesRise_first_name);
                            TextView salesRiseFirstValue = root.findViewById(R.id.home_salesRise_first_value);
                            TextView salesRiseSecondName = root.findViewById(R.id.home_salesRise_second_name);
                            TextView salesRiseSecondValue = root.findViewById(R.id.home_salesRise_second_value);
                            if (parentView.getVisibility() == View.GONE || parentView.getVisibility() == View.INVISIBLE) {
                                if (products.size() == 1) {
                                    salesRiseFirstName.setText(products.get(0).getProduct_name());
                                    salesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                } else if (products.size() > 1) {
                                    salesRiseFirstName.setText(products.get(0).getProduct_name());
                                    salesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                    salesRiseSecondName.setText(products.get(1).getProduct_name());
                                    salesRiseSecondValue.setText(String.valueOf(products.get(1).getQuantity_sold()));
                                }
                                parentView.setVisibility(View.VISIBLE);
                            } else {
                                parentView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });


        root.findViewById(R.id.queda_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View parentView = root.findViewById(R.id.home_salesDrop_hidden);
                if (homeViewModel.getProductsSorted() != null) {
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            int arrSize = products.size();
                            TextView salesDropFirstName = root.findViewById(R.id.home_salesDrop_first_name);
                            TextView salesDropFirstValue = root.findViewById(R.id.home_salesDrop_first_value);
                            TextView salesDropSecondName = root.findViewById(R.id.home_salesDrop_second_name);
                            TextView salesDropSecondValue = root.findViewById(R.id.home_salesDrop_second_value);
                            if (parentView.getVisibility() == View.GONE) {
                                if (arrSize > 1) {
                                    salesDropFirstName.setText(products.get(arrSize - 1).getProduct_name());
                                    salesDropFirstValue.setText(String.valueOf(products.get(arrSize - 1).getQuantity_sold()));
                                    salesDropSecondName.setText(products.get(arrSize - 2).getProduct_name());
                                    salesDropSecondValue.setText(String.valueOf(products.get(arrSize - 2).getQuantity_sold()));
                                } else if (arrSize == 1) {
                                    salesDropFirstName.setText(products.get(arrSize - 1).getProduct_name());
                                    salesDropFirstValue.setText(String.valueOf(products.get(arrSize - 1).getQuantity_sold()));
                                }
                                parentView.setVisibility(View.VISIBLE);
                            } else {
                                parentView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });


        root.findViewById(R.id.total_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentView = root.findViewById(R.id.home_profit_hidden);
                if(parentView.getVisibility() == View.GONE || parentView.getVisibility() == View.INVISIBLE){
                    boolean hasProfit = false;

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_DAY) != null){
                        homeViewModel.getSalesSince(HomeViewModel.ONE_DAY).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                TextView dailyProfit = root.findViewById(R.id.daily_profit);
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices){
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalProfit);
                                dailyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    }else{
                        View linearLayoutDaily = root.findViewById(R.id.linearLayout_daily_profit);
                        linearLayoutDaily.setVisibility(View.GONE);
                    }

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK) != null){
                        homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                TextView weeklyProfit = root.findViewById(R.id.weekly_profit);
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices){
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalProfit);
                                weeklyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    }else {
                        View linearLayoutWeekly = root.findViewById(R.id.linearLayout_weekly_profit);
                        linearLayoutWeekly.setVisibility(View.GONE);
                    }

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH) != null){
                        homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                TextView monthlyProfit = root.findViewById(R.id.monthly_profit);
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices){
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalProfit);
                                monthlyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    }else {
                        View linearLayoutMonthly = root.findViewById(R.id.linearLayout_monthly_profit);
                        linearLayoutMonthly.setVisibility(View.GONE);
                    }

                    if (hasProfit){
                        parentView.setVisibility(View.VISIBLE);
                    }
                }else{
                    parentView.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }
}
