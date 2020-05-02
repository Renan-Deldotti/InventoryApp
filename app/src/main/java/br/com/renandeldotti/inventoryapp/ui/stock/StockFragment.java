package br.com.renandeldotti.inventoryapp.ui.stock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import br.com.renandeldotti.inventoryapp.ProductsAdapter;
import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        stockViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StockViewModel.class);
        RecyclerView recyclerView = root.findViewById(R.id.stock_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProductsAdapter adapter = new ProductsAdapter();
        recyclerView.setAdapter(adapter);
        try {
            // Null object
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
        return root;
    }
}
