package br.com.renandeldotti.inventoryapp.ui.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class SalesFragment extends Fragment {

    private SalesViewModel salesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_sales, container, false);
        RecyclerView salesRecyclerView = root.findViewById(R.id.sales_recyclerView);
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        salesRecyclerView.setHasFixedSize(true);
        final SalesAdapter adapter = new SalesAdapter();
        salesRecyclerView.setAdapter(adapter);
        try {
            if (salesViewModel.getAllSales() != null) {
                salesViewModel.getAllSales().observe(getViewLifecycleOwner(), new Observer<List<Sold>>() {
                    @Override
                    public void onChanged(List<Sold> solds) {
                        adapter.setAllSales(solds);
                    }
                });
            }
            ProgressBar progressBar = root.findViewById(R.id.sales_progressbar);
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(SalesFragment.class.getSimpleName(), "Error" + e);
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sales_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_hist:
                Toast.makeText(getContext(), "Cleared", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
