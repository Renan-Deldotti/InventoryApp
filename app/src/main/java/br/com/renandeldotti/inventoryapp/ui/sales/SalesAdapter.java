package br.com.renandeldotti.inventoryapp.ui.sales;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesHolder> {

    private List<Sold> allSales;
    //private List<Products> allProducts;

    @NonNull
    @Override
    public SalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sold_list_item,parent,false);
        return new SalesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesHolder holder, int position) {
        if (allSales != null){
            Sold sold = allSales.get(position);
            holder.soldProductTv.setText(String.valueOf(sold.getProduct_name()));
            holder.soldQuantity.setText(String.valueOf(sold.getQuantity_sold()));
            holder.soldPrice.setText(String.valueOf(sold.getSold_price()));
        }else{
            Log.e(SalesAdapter.class.getSimpleName(),"Null list");
        }
    }

    @Override
    public int getItemCount() {
        if (allSales != null){
            return allSales.size();
        }else {
            return 0;
        }
    }

    public void setAllSales(List<Sold> allSales) {
        this.allSales = allSales;
        notifyDataSetChanged();
    }

    public class SalesHolder extends RecyclerView.ViewHolder{

        private TextView soldProductTv,soldQuantity,soldPrice,soldDate,soldTotalPrice;

        public SalesHolder(@NonNull View itemView) {
            super(itemView);
            soldProductTv = itemView.findViewById(R.id.sold_product_id);
            soldQuantity = itemView.findViewById(R.id.sold_product_quantity);
            soldPrice = itemView.findViewById(R.id.sold_product_price);
            soldDate = itemView.findViewById(R.id.sold_product_date);
            soldTotalPrice = itemView.findViewById(R.id.sold_total_price);
        }
    }
}
