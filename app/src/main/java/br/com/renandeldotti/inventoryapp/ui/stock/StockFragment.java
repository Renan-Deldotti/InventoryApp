package br.com.renandeldotti.inventoryapp.ui.stock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.com.renandeldotti.inventoryapp.AddEditProduct;
import br.com.renandeldotti.inventoryapp.ProductsAdapter;
import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.databinding.FragmentStockBinding;
import br.com.renandeldotti.inventoryapp.ui.sellproduct.SellProduct;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;
    private FragmentStockBinding stockBinding;
    private static final String TAG = StockFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stockBinding = FragmentStockBinding.inflate(inflater, container, false);
        View root = stockBinding.getRoot();

        setHasOptionsMenu(true);
        setMenuVisibility(false);

        if (getActivity() != null)
            stockViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StockViewModel.class);

        final ProductsAdapter adapter = new ProductsAdapter(getContext());
        stockBinding.stockRv.setLayoutManager(new LinearLayoutManager(getContext()));
        stockBinding.stockRv.setHasFixedSize(true);
        stockBinding.stockRv.setAdapter(adapter);
        try {
            stockViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                @Override
                public void onChanged(List<Products> products) {
                    adapter.submitList(products);
                }
            });
            stockBinding.stockProgressbar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
        }
        adapter.productsSetOnItemClickListener(new ProductsAdapter.productsOnItemClickListener() {
            @Override
            public void onItemClick(Products products) {
                Intent intent = new Intent(getContext(), AddEditProduct.class);
                intent.putExtra(AddEditProduct.EXTRA_ID, products.getId());
                intent.putExtra(AddEditProduct.EXTRA_DESCRIPTION, products.getProduct_description());
                intent.putExtra(AddEditProduct.EXTRA_PRICE, products.getPrice());
                intent.putExtra(AddEditProduct.EXTRA_QUANTITY, products.getProduct_quantity());
                intent.putExtra(AddEditProduct.EXTRA_NAME, products.getProduct_name());
                intent.putExtra(AddEditProduct.EXTRA_QUANTITY_SOLD, products.getQuantity_sold());
                startActivity(intent);
            }
        });
        adapter.productsSetOnLongClickListener(new ProductsAdapter.productsOnLongClickListener() {
            @Override
            public void onLongClick(View view, Products products) {
                final Products inMethodProduct = products;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(getResources().getStringArray(R.array.edit_or_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                stockViewModel.delete(inMethodProduct);
                                break;
                            case 1:
                                Intent intent = new Intent(getContext(), AddEditProduct.class);
                                intent.putExtra(AddEditProduct.EXTRA_ID, inMethodProduct.getId());
                                intent.putExtra(AddEditProduct.EXTRA_DESCRIPTION, inMethodProduct.getProduct_description());
                                intent.putExtra(AddEditProduct.EXTRA_PRICE, inMethodProduct.getPrice());
                                intent.putExtra(AddEditProduct.EXTRA_QUANTITY, inMethodProduct.getProduct_quantity());
                                intent.putExtra(AddEditProduct.EXTRA_NAME, inMethodProduct.getProduct_name());
                                intent.putExtra(AddEditProduct.EXTRA_QUANTITY_SOLD, inMethodProduct.getQuantity_sold());
                                startActivity(intent);
                                break;
                            case 2:
                                Intent intent1 = new Intent(getContext(), SellProduct.class);
                                intent1.putExtra(AddEditProduct.EXTRA_ID, inMethodProduct.getId());
                                intent1.putExtra(AddEditProduct.EXTRA_DESCRIPTION, inMethodProduct.getProduct_description());
                                intent1.putExtra(AddEditProduct.EXTRA_PRICE, inMethodProduct.getPrice());
                                intent1.putExtra(AddEditProduct.EXTRA_QUANTITY, inMethodProduct.getProduct_quantity());
                                intent1.putExtra(AddEditProduct.EXTRA_NAME, inMethodProduct.getProduct_name());
                                intent1.putExtra(AddEditProduct.EXTRA_QUANTITY_SOLD, inMethodProduct.getQuantity_sold());
                                startActivity(intent1);
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        return root;

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.long_press_option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
