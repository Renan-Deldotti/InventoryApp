package br.com.renandeldotti.inventoryapp.ui.stock;

import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import br.com.renandeldotti.inventoryapp.AddEditProduct;
import br.com.renandeldotti.inventoryapp.ProductsAdapter;
import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;
    private Products productToEdit;

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
//                Toast.makeText(getContext(), ""+products.getProduct_name(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Hold to edit", Toast.LENGTH_SHORT).show();
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
                //stockViewModel.delete(products);
                Toast.makeText(getContext(),products.getProduct_name()+"", Toast.LENGTH_SHORT).show();
                //setMenuVisibility(true);
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
