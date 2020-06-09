package br.com.renandeldotti.inventoryapp.ui.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Sold;
import br.com.renandeldotti.inventoryapp.databinding.FragmentSalesBinding;

public class SalesFragment extends Fragment {

    private SalesViewModel salesViewModel;
    private FragmentSalesBinding salesBinding;
    private static final String TAG = SalesFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        salesBinding = FragmentSalesBinding.inflate(inflater,container,false);
        View root = salesBinding.getRoot();

        /*if (getActivity() != null)
        salesViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SalesViewModel.class);*/

        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

        final SalesAdapter adapter = new SalesAdapter(getContext());
        salesBinding.salesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        salesBinding.salesRecyclerView.setHasFixedSize(true);
        salesBinding.salesRecyclerView.setAdapter(adapter);
        try {
            if (salesViewModel.getAllSales() != null) {
                salesViewModel.getAllSales().observe(getViewLifecycleOwner(), new Observer<List<Sold>>() {
                    @Override
                    public void onChanged(List<Sold> solds) {
                        adapter.setAllSales(solds);
                    }
                });
            }
            salesBinding.salesProgressbar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG,"Error" + e);
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
