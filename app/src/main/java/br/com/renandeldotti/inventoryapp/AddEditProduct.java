package br.com.renandeldotti.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
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

import br.com.renandeldotti.inventoryapp.database.Products;
import br.com.renandeldotti.inventoryapp.database.ProductsViewModel;

public class AddEditProduct extends AppCompatActivity {

    private static final int NAV_HOME = 1;
    private static final int NAV_CANCEL = 2;

    public static final String EXTRA_ID = "br.com.renandeldotti.inventoryapp.EXTRA_ID";
    public static final String EXTRA_NAME = "br.com.renandeldotti.inventoryapp.EXTRA_NAME";
    public static final String EXTRA_QUANTITY = "br.com.renandeldotti.inventoryapp.EXTRA_QUANTITY";
    public static final String EXTRA_PRICE = "br.com.renandeldotti.inventoryapp.EXTRA_PRICE";
    public static final String EXTRA_DESCRIPTION = "br.com.renandeldotti.inventoryapp.EXTRA_DESCRIPTION";

    private Intent intentCall;
    private ProductsViewModel productsViewModel;

    private TextInputEditText productName;
    private EditText productQuantity, productPrice, productDescription;
    private Button clearFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        intentCall = getIntent();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_edit_cancel:
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditProduct.this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareToSave() {
        String addName = productName.getText().toString().trim();
        if (TextUtils.isEmpty(addName) || addName.length() <= 3){
            Toast.makeText(this, getResources().getString(R.string.invalid_name), Toast.LENGTH_SHORT).show();
            productName.setError(getResources().getString(R.string.invalid_name));
            return;
        }
        int addQuantity = 0;
        try {
             addQuantity = Integer.parseInt(productQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_quantity), Toast.LENGTH_SHORT).show();
            productQuantity.setError(getResources().getString(R.string.invalid_quantity));
            return;
        }
        float addPrice = 0;
        try {
            addPrice = Float.parseFloat(productPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_price), Toast.LENGTH_SHORT).show();
            productPrice.setError(getResources().getString(R.string.invalid_price));
            return;
        }
        String addDescription = productDescription.getText().toString().trim();

        /*Intent data = new Intent();
        data.putExtra(EXTRA_NAME,addName);
        data.putExtra(EXTRA_QUANTITY,addQuantity);
        data.putExtra(EXTRA_PRICE,addPrice);
        data.putExtra(EXTRA_DESCRIPTION,addDescription);

        setResult(RESULT_OK,data);
        finish();*/

        productsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProductsViewModel.class);
        Products products = new Products(addName,addDescription,addQuantity,addPrice);
        productsViewModel.insert(products);
        setResult(RESULT_OK);
        finish();
    }
}
