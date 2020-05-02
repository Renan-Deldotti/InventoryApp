package br.com.renandeldotti.inventoryapp.ui.sales;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import br.com.renandeldotti.inventoryapp.database.Sold;

public class SalesAdapter extends ListAdapter<Sold, SalesAdapter.SalesHolder> {

    protected SalesAdapter(@NonNull DiffUtil.ItemCallback<Sold> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public SalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SalesHolder holder, int position) {

    }

    class SalesHolder extends RecyclerView.ViewHolder{

        private TextView soldProductTv,soldQuantity,soldPrice,soldDate,soldTotalPrice;

        public SalesHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
