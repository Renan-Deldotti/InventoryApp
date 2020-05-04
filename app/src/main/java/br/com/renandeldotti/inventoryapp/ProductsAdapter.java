package br.com.renandeldotti.inventoryapp;

import android.content.Context;
import android.icu.util.Currency;
import android.os.Vibrator;
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

    private productsOnItemClickListener listener;
    private productsOnLongClickListener longClickListener;
    private Context context;

    public ProductsAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    public ProductsAdapter(Context context){
        super(DIFF_ITEM_CALLBACK);
        this.context = context;
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
        holder.quantityTv.setText(String.valueOf(products.getProduct_quantity()));
        String formattedStr = "R$: " + String.format(Locale.getDefault(),"%.2f",products.getPrice());
        holder.priceTv.setText(formattedStr);
    }

    Products getProductAtPosition(int position){return getItem(position);}

    class ProductsHolder extends RecyclerView.ViewHolder{
        private TextView titleTv,quantityTv,priceTv;
        private View thisView;

        public ProductsHolder(@NonNull View itemView) {
            super(itemView);
            thisView = itemView;
            titleTv = itemView.findViewById(R.id.product_title);
            quantityTv = itemView.findViewById(R.id.product_quantity);
            priceTv = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (context != null && position != RecyclerView.NO_POSITION) {
                        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator != null)
                            vibrator.vibrate(50);
                    }else{return false;} // Verificar necessidade de retorno
                    if (longClickListener != null){
                        longClickListener.onLongClick(thisView,getItem(position));
                    }
                    // False ---> faz com que clickListener normal seja chamado
                    // True ----> faz com que apenas o LongClick seja chamado
                    return true;
                }
            });
        }
    }
    public interface productsOnItemClickListener{
        void onItemClick(Products products);
    }
    public void productsSetOnItemClickListener(productsOnItemClickListener listener){
        this.listener = listener;
    }
    public interface productsOnLongClickListener{
        void onLongClick(View view, Products products);
    }
    public void productsSetOnLongClickListener(productsOnLongClickListener listener){
        this.longClickListener = listener;
    }
}