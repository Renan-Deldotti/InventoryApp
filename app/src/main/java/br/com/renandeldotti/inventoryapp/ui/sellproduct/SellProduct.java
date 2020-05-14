package br.com.renandeldotti.inventoryapp.ui.sellproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;

import br.com.renandeldotti.inventoryapp.AddEditProduct;
import br.com.renandeldotti.inventoryapp.R;

public class SellProduct extends AppCompatActivity {

    private TextView textViewName, textViewDescription, textViewProductPrice, textViewQuantity, textViewTotalPrice;
    private Button buttonSend;
    private float unitPrice = 0f;
    private int quantity = 0;
    private float totalPrice = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        Intent intent = getIntent();
        if (!intent.hasExtra(AddEditProduct.EXTRA_ID)){
            return;
        }

        textViewName = findViewById(R.id.sell_product_name);
        textViewDescription = findViewById(R.id.sell_product_description);
        textViewProductPrice = findViewById(R.id.sell_product_price);
        textViewQuantity = findViewById(R.id.sell_product_quantity);
        textViewTotalPrice = findViewById(R.id.sell_product_totalPrice);

        textViewName.setText(intent.getStringExtra(AddEditProduct.EXTRA_NAME));
        textViewDescription.setText(intent.getStringExtra(AddEditProduct.EXTRA_DESCRIPTION));

        unitPrice = intent.getFloatExtra(AddEditProduct.EXTRA_PRICE,0);
        String unitPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",unitPrice);
        textViewProductPrice.setText(unitPriceSet);

        quantity = intent.getIntExtra(AddEditProduct.EXTRA_QUANTITY,0);
        textViewQuantity.setText(String.valueOf(quantity));

        totalPrice = unitPrice * quantity;

        String totalPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalPrice);
        textViewTotalPrice.setText(totalPriceSet);



        buttonSend = findViewById(R.id.sell_product_buttonSell);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPrice = unitPrice * quantity * 2;
                String totalPriceSet = Currency.getInstance(Locale.getDefault()).getSymbol() + String.format(Locale.getDefault(),"%.2f",totalPrice);
                textViewTotalPrice.setText(totalPriceSet);
            }
        });
    }
}
