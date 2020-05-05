package br.com.renandeldotti.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsViewModel;
import br.com.renandeldotti.inventoryapp.database.Sold;

public class AddEditProduct extends AppCompatActivity {

    public static final String EXTRA_ID = "br.com.renandeldotti.inventoryapp.EXTRA_ID";
    public static final String EXTRA_NAME = "br.com.renandeldotti.inventoryapp.EXTRA_NAME";
    public static final String EXTRA_QUANTITY = "br.com.renandeldotti.inventoryapp.EXTRA_QUANTITY";
    public static final String EXTRA_PRICE = "br.com.renandeldotti.inventoryapp.EXTRA_PRICE";
    public static final String EXTRA_DESCRIPTION = "br.com.renandeldotti.inventoryapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_QUANTITY_SOLD = "br.com.renandeldotti.inventoryapp.EXTRA_QUANTITY_SOLD";

    private Intent intentCall;
    private ProductsViewModel productsViewModel;
    private int defaultIdVal = -1;
    private int thisProductId = defaultIdVal;
    private boolean hasId = false;

    private TextInputEditText productName;
    private EditText productQuantity, productPrice, productDescription;
    private Button clearFields;
    private TextView editTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        productsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProductsViewModel.class);

        intentCall = getIntent();
        if (intentCall.hasExtra(EXTRA_ID)){
            hasId = true;
            thisProductId = intentCall.getIntExtra(EXTRA_ID,defaultIdVal);
        }

        productName = findViewById(R.id.add_product_name);
        productQuantity = findViewById(R.id.add_product_quantity);
        productPrice = findViewById(R.id.add_product_price);
        productDescription = findViewById(R.id.add_product_description);
        clearFields = findViewById(R.id.btn_clearFields);

        clearFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productName.setText("");
                productQuantity.setText("");
                productPrice.setText("");
                productDescription.setText("");
            }
        });

        if (hasId){
            setTitle(getResources().getString(R.string.edit_product));
            editTitleTV = findViewById(R.id.edit_add_title_textView);
            editTitleTV.setText(getResources().getString(R.string.edit_product));
            populateFields();
        }else{
            setTitle(getResources().getString(R.string.add_product));
        }
    }

    private void populateFields() {
        String nameToSet = intentCall.getStringExtra(EXTRA_NAME);
        productName.setText(nameToSet);
        String quantityToSet = String.valueOf(intentCall.getIntExtra(EXTRA_QUANTITY,0));
        productQuantity.setText(quantityToSet);
        Float priceFloat = intentCall.getFloatExtra(EXTRA_PRICE,0);
        String productPriceToSet = String.format(Locale.getDefault(),"%.2f",priceFloat);
        productPrice.setText(productPriceToSet);
        String productDescriptionToSet = intentCall.getStringExtra(EXTRA_DESCRIPTION);
        productDescription.setText(productDescriptionToSet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!hasId){
            menu.removeItem(R.id.add_edit_delete);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditProduct.this);
        switch (item.getItemId()) {
            case R.id.add_edit_cancel:
                builder.setMessage(R.string.exit_message);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(AddEditProduct.this);
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
                return true;
            case R.id.add_edit_save:
                prepareToSave();
                return true;
            case R.id.add_edit_delete:
                if (!hasId){
                    return true;
                }
                builder.setMessage(getResources().getString(R.string.delete_confirm));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteThisProduct();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no),null);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteThisProduct() {
        if (thisProductId == -1){
            Toast.makeText(this, getResources().getString(R.string.error_delete), Toast.LENGTH_SHORT).show();
            return;
        }
        productsViewModel.deleteSingleItem(thisProductId);
        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(AddEditProduct.this);
    }

    private void prepareToSave() {
        String addName = ""+productName.getText().toString().trim();
        if (TextUtils.isEmpty(addName) || addName.length() <= 3){
            Toast.makeText(this, getResources().getString(R.string.invalid_name), Toast.LENGTH_SHORT).show();
            productName.setError(getResources().getString(R.string.invalid_name));
            return;
        }
        int addQuantity;
        try {
             addQuantity = Integer.parseInt(productQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_quantity), Toast.LENGTH_SHORT).show();
            productQuantity.setError(getResources().getString(R.string.invalid_quantity));
            return;
        }
        float addPrice;
        try {
            String s1 = productPrice.getText().toString().trim();
            s1 = s1.replace(',','.');
            addPrice = Float.parseFloat(s1.trim());
            Log.e(AddEditProduct.class.getSimpleName(),"val in Float:\t"+addPrice);
            if (Float.isInfinite(addPrice) || Float.isNaN(addPrice) || addPrice >= Float.MAX_VALUE){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_price), Toast.LENGTH_SHORT).show();
            productPrice.setError(getResources().getString(R.string.invalid_price));
            return;
        }
        
        String addDescription = productDescription.getText().toString().trim();

        boolean wasInserted = false;
        try {
            Products products = new Products(addName,addDescription,addQuantity,addPrice);
            if (!hasId){
                productsViewModel.insert(products);
                Toast.makeText(this, getResources().getString(R.string.product_saved), Toast.LENGTH_SHORT).show();
            }else{
                products.setId(thisProductId);
                productsViewModel.update(products);
                Toast.makeText(this, getResources().getString(R.string.product_updated), Toast.LENGTH_SHORT).show();
            }
            wasInserted = true;
        } catch (Exception e) {
            Log.e(AddEditProduct.class.getSimpleName(),"Something went wrong.");
        }
        if (wasInserted && hasId){
            int quantitySold = 0;
            int oldQuantity = intentCall.getIntExtra(EXTRA_QUANTITY,0);
            if (addQuantity < oldQuantity){
                 quantitySold = oldQuantity - addQuantity;
                if (quantitySold < 0){
                    quantitySold = oldQuantity;
                }
            }
            String dateAdded = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            if (quantitySold != 0) {
                Sold sold = new Sold(addName, addPrice, quantitySold, dateAdded);
                productsViewModel.insertNewSale(sold);
                Log.e("Added","Added: true");
            }
        }
        setResult(RESULT_OK);
        finish();
    }
}
