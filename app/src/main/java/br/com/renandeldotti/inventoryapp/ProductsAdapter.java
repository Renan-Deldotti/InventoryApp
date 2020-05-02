package br.com.renandeldotti.inventoryapp;

import android.icu.util.Currency;
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

import java.util.Locale;

import br.com.renandeldotti.inventoryapp.database.Products;

public class ProductsAdapter extends ListAdapter<Products, ProductsAdapter.ProductsHolder> {

    protected ProductsAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    static final DiffUtil.ItemCallback<Products> DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<Products>() {
        @Override
        public boolean areItemsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return oldItem.getProduct_name().equals(newItem.getProduct_name()) &&
                    oldItem.getProduct_description().equals(newItem.getProduct_description()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getProduct_quantity() == newItem.getProduct_quantity();
        }
    };

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_single_item,parent,false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        Products products = getItem(position);
        holder.titleTv.setText(products.getProduct_name());
        holder.quantityTv.setText(products.getProduct_quantity());
        String priceString = "R$ "+ products.getPrice();
        holder.priceTv.setText(priceString);
    }

    Products getProductAtPosition(int position){return getItem(position);}

    class ProductsHolder extends RecyclerView.ViewHolder{
        private TextView titleTv,quantityTv,priceTv;

        public ProductsHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.product_title);
            quantityTv = itemView.findViewById(R.id.product_quantity);
            priceTv = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(Products.class.getSimpleName(),"item position clicked: "+getAdapterPosition());
                }
            });
        }
    }

}