package br.com.renandeldotti.inventoryapp.ui.sellproduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import br.com.renandeldotti.inventoryapp.AddEditProduct;
import br.com.renandeldotti.inventoryapp.R;
import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class SellProduct extends AppCompatActivity {

    private TextView textViewName, textViewDescription, textViewProductPrice, textViewQuantity, textViewTotalPrice, textViewInStock;
    private Button buttonSell,buttonPlus,buttonMinus;
    private float unitPrice = 0f;
    private int quantity = 0,maxQuantity = 0;
    private float totalPrice = 0f;

    private SellProductViewModel viewModel;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        intent = getIntent();
        if (!intent.hasExtra(AddEditProduct.EXTRA_ID)){
            return;
        }

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SellProductViewModel.class);

        textViewName = findViewById(R.id.sell_product_name);
        textViewDescription = findViewById(R.id.sell_product_description);
        textViewProductPrice = findViewById(R.id.sell_product_price);
        textViewQuantity = findViewById(R.id.sell_product_quantity);
        textViewTotalPrice = findViewById(R.id.sell_product_totalPrice);
        textViewInStock = findViewById(R.id.sell_product_inStock);

        textViewName.setText(intent.getStringExtra(AddEditProduct.EXTRA_NAME));
        textViewDescription.setText(intent.getStringExtra(AddEditProduct.EXTRA_DESCRIPTION));

        unitPrice = intent.getFloatExtra(AddEditProduct.EXTRA_PRICE,0);
        String unitPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",unitPrice);
        textViewProductPrice.setText(unitPriceSet);

        maxQuantity = intent.getIntExtra(AddEditProduct.EXTRA_QUANTITY,0);
        textViewInStock.setText(String.valueOf(maxQuantity));

        textViewQuantity.setText(String.valueOf(quantity));

        String totalPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalPrice);
        textViewTotalPrice.setText(totalPriceSet);


        buttonMinus = findViewById(R.id.sell_product_minusButton);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intQuantity = checkForQuantity();
                if (intQuantity == -1){
                    return;
                }else if(intQuantity == 0){
                    Toast.makeText(SellProduct.this, "Quantity is 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                intQuantity--;
                textViewQuantity.setText(String.valueOf(intQuantity));
                setNewTotalPrice();
            }
        });

        buttonPlus = findViewById(R.id.sell_product_plusButton);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intQuantity = checkForQuantity();
                if (intQuantity == -1){
                    return;
                }else if(intQuantity == maxQuantity){
                    Toast.makeText(SellProduct.this, "No more products", Toast.LENGTH_SHORT).show();
                    return;
                }
                intQuantity++;
                textViewQuantity.setText(String.valueOf(intQuantity));
                setNewTotalPrice();
            }
        });


        buttonSell = findViewById(R.id.sell_product_buttonSell);
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intQuantity = checkForQuantity();
                if (intQuantity <=0){
                    Toast.makeText(SellProduct.this, "Can not sell "+intQuantity+" products", Toast.LENGTH_SHORT).show();
                    }else {
                        Sold sold = new Sold(intent.getStringExtra(AddEditProduct.EXTRA_NAME),unitPrice,intQuantity,new Date().getTime());
                        Products products = new Products(intent.getStringExtra(AddEditProduct.EXTRA_NAME),intent.getStringExtra(AddEditProduct.EXTRA_DESCRIPTION),maxQuantity-intQuantity,unitPrice);
                        products.setId(intent.getIntExtra(AddEditProduct.EXTRA_ID,-1));
                        int newQuantitySold = intent.getIntExtra(AddEditProduct.EXTRA_QUANTITY_SOLD,-1) - intQuantity;
                        products.setQuantity_sold(newQuantitySold);
                        if ((products.getId() == -1) || (products.getQuantity_sold() == -1)){
                            return;
                        }
                        viewModel.makeNewSale(products, sold);
                        Toast.makeText(SellProduct.this, "Sold for "+textViewTotalPrice.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void setNewTotalPrice(){
        int intQuantity;
        try {
            intQuantity = Integer.parseInt(textViewQuantity.getText().toString().trim());
        }catch (Exception e){
            intQuantity = -1;
        }
        if (intQuantity < 0){
            return;
        }

        totalPrice = unitPrice * intQuantity;
        String totalPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalPrice);
        textViewTotalPrice.setText(totalPriceSet);
    }

    private int checkForQuantity(){
        int intQuantity;
        try {
            intQuantity = Integer.parseInt(textViewQuantity.getText().toString().trim());
        }catch (Exception e){
            intQuantity = -1;
        }
        return intQuantity;
    }
}
