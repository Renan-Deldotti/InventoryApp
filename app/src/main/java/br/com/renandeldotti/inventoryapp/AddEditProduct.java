package br.com.renandeldotti.inventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddEditProduct extends AppCompatActivity {

    private static final int NAV_HOME = 1;
    private static final int NAV_CANCEL = 2;

    private TextInputEditText productName;
    private EditText productQuantity, productPrice, productDescription;
    private Button clearFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);
        productName = findViewById(R.id.add_product_name);
        productQuantity = findViewById(R.id.add_product_quantity);
        productPrice = findViewById(R.id.add_product_price);
        productDescription = findViewById(R.id.add_product_description);
        clearFields = findViewById(R.id.btn_clearFields);
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
        try {
            int addQuantity = Integer.parseInt(productQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_quantity), Toast.LENGTH_SHORT).show();
            productQuantity.setError(getResources().getString(R.string.invalid_quantity));
            return;
        }
        try {
            int addPrice = Integer.parseInt(productPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this,getResources().getString(R.string.invalid_price), Toast.LENGTH_SHORT).show();
            productQuantity.setError(getResources().getString(R.string.invalid_price));
            return;
        }
        String addDescription = productDescription.getText().toString().trim();
    }
}
