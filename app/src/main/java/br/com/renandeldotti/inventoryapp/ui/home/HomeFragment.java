package br.com.renandeldotti.inventoryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.QuantityAndPrice;
import br.com.renandeldotti.inventoryapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    //private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding homeBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        final View root = homeBinding.getRoot();
        if (getActivity() != null)
            homeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);

        homeBinding.ritmoVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeBinding.homeRateSalesHidden.getVisibility() == View.VISIBLE) {
                    homeBinding.homeRateSalesHidden.setVisibility(View.GONE);
                } else {
                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_DAY) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_DAY).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                long soldQuantity = 0;
                                for (QuantityAndPrice qp : quantityAndPrices) {
                                    soldQuantity += qp.getQuantity_sold();
                                }
                                homeBinding.homeTodaySalesValue.setText(String.valueOf(soldQuantity));
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
                                homeBinding.homeWeekSalesValue.setText(String.valueOf(soldQuantity));
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
                                homeBinding.homeMonthSalesValue.setText(String.valueOf(soldQuantity));
                            }
                        });
                    }
                    homeBinding.homeRateSalesHidden.setVisibility(View.VISIBLE);
                }
            }
        });


        homeBinding.aumentoVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View parentView = homeBinding.homeSalesRiseHidden;

                if (homeViewModel.getProductsSorted() != null) {
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            if (parentView.getVisibility() == View.GONE || parentView.getVisibility() == View.INVISIBLE) {
                                if (products.size() == 1) {
                                    homeBinding.homeSalesRiseFirstName.setText(products.get(0).getProduct_name());
                                    homeBinding.homeSalesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                } else if (products.size() > 1) {
                                    homeBinding.homeSalesRiseFirstName.setText(products.get(0).getProduct_name());
                                    homeBinding.homeSalesRiseFirstValue.setText(String.valueOf(products.get(0).getQuantity_sold()));
                                    homeBinding.homeSalesRiseSecondName.setText(products.get(1).getProduct_name());
                                    homeBinding.homeSalesRiseSecondValue.setText(String.valueOf(products.get(1).getQuantity_sold()));
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


        homeBinding.quedaVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View parentView = homeBinding.homeSalesDropHidden;
                if (homeViewModel.getProductsSorted() != null) {
                    homeViewModel.getProductsSorted().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> products) {
                            int arrSize = products.size();
                            if (parentView.getVisibility() == View.GONE) {
                                if (arrSize > 1) {
                                    homeBinding.homeSalesDropFirstName.setText(products.get(arrSize - 1).getProduct_name());
                                    homeBinding.homeSalesDropFirstValue.setText(String.valueOf(products.get(arrSize - 1).getQuantity_sold()));
                                    homeBinding.homeSalesDropSecondName.setText(products.get(arrSize - 2).getProduct_name());
                                    homeBinding.homeSalesDropSecondValue.setText(String.valueOf(products.get(arrSize - 2).getQuantity_sold()));
                                } else if (arrSize == 1) {
                                    homeBinding.homeSalesDropFirstName.setText(products.get(arrSize - 1).getProduct_name());
                                    homeBinding.homeSalesDropFirstValue.setText(String.valueOf(products.get(arrSize - 1).getQuantity_sold()));
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


        homeBinding.totalVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentView = homeBinding.homeProfitHidden;
                if (parentView.getVisibility() == View.GONE || parentView.getVisibility() == View.INVISIBLE) {
                    boolean hasProfit = false;

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_DAY) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_DAY).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices) {
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(), "%.2f", totalProfit);
                                homeBinding.dailyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    } else {
                        homeBinding.linearLayoutDailyProfit.setVisibility(View.GONE);
                    }

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_WEEK).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices) {
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(), "%.2f", totalProfit);
                                homeBinding.weeklyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    } else {
                        homeBinding.linearLayoutWeeklyProfit.setVisibility(View.GONE);
                    }

                    if (homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH) != null) {
                        homeViewModel.getSalesSince(HomeViewModel.ONE_MONTH).observe(getViewLifecycleOwner(), new Observer<List<QuantityAndPrice>>() {
                            @Override
                            public void onChanged(List<QuantityAndPrice> quantityAndPrices) {
                                float totalProfit = 0f;
                                for (QuantityAndPrice quantityAndPrice : quantityAndPrices) {
                                    totalProfit += (quantityAndPrice.getQuantity_sold() * quantityAndPrice.getSold_price());
                                }
                                String formattedValue = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(), "%.2f", totalProfit);
                                homeBinding.monthlyProfit.setText(formattedValue);
                            }
                        });
                        hasProfit = true;
                    } else {
                        homeBinding.linearLayoutMonthlyProfit.setVisibility(View.GONE);
                    }

                    if (hasProfit) {
                        parentView.setVisibility(View.VISIBLE);
                    }
                } else {
                    parentView.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }
}
