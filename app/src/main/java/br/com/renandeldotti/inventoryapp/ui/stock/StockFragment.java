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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renandeldotti.inventoryapp.AddEditProduct;
import br.com.renandeldotti.inventoryapp.ProductsAdapter;
import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        setHasOptionsMenu(true);
        setMenuVisibility(false);
        stockViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StockViewModel.class);
        final RecyclerView recyclerView = root.findViewById(R.id.stock_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProductsAdapter adapter = new ProductsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        try {
            stockViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
                @Override
                public void onChanged(List<Products> products) {
                    adapter.submitList(products);
                }
            });
            ProgressBar progressBar = root.findViewById(R.id.stock_progressbar);
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(StockFragment.class.getSimpleName(),"Error: "+e);
        }
        adapter.productsSetOnItemClickListener(new ProductsAdapter.productsOnItemClickListener() {
            @Override
            public void onItemClick(Products products) {
                Intent intent = new Intent(getContext(), AddEditProduct.class);
                intent.putExtra(AddEditProduct.EXTRA_ID,products.getId());
                intent.putExtra(AddEditProduct.EXTRA_DESCRIPTION,products.getProduct_description());
                intent.putExtra(AddEditProduct.EXTRA_PRICE,products.getPrice());
                intent.putExtra(AddEditProduct.EXTRA_QUANTITY,products.getProduct_quantity());
                intent.putExtra(AddEditProduct.EXTRA_NAME,products.getProduct_name());
                intent.putExtra(AddEditProduct.EXTRA_QUANTITY_SOLD,products.getQuantity_sold());
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
                        if (which == 0){
                            stockViewModel.delete(inMethodProduct);
                        }else{
                            Intent intent = new Intent(getContext(), AddEditProduct.class);
                            intent.putExtra(AddEditProduct.EXTRA_ID,inMethodProduct.getId());
                            intent.putExtra(AddEditProduct.EXTRA_DESCRIPTION,inMethodProduct.getProduct_description());
                            intent.putExtra(AddEditProduct.EXTRA_PRICE,inMethodProduct.getPrice());
                            intent.putExtra(AddEditProduct.EXTRA_QUANTITY,inMethodProduct.getProduct_quantity());
                            intent.putExtra(AddEditProduct.EXTRA_NAME,inMethodProduct.getProduct_name());
                            intent.putExtra(AddEditProduct.EXTRA_QUANTITY_SOLD,inMethodProduct.getQuantity_sold());
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        /*Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setMenuVisibility(true);
            }
        };
        thread.start();*/
        return root;

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.long_press_option_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
