package br.com.renandeldotti.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_stock, R.id.navigation_sales)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        FloatingActionButton fab = findViewById(R.id.add_product);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditProduct.class);
                startActivity(intent);
            }
        });

        // Todo: Continuar a edição do botão de exlucsão

        /* Atualiza a lista ao tempo determinado pelo usuario
        Timer timer = new Timer();
        // time in miliseconds (1000 = 1s)
        long timeToReset;
        try {
            final double i = (1000*60*60)*1.5;
            String s = String.valueOf(i);
            s = s.substring(0,(s.length()-2));
            timeToReset = Long.parseLong(s);
        } catch (NumberFormatException e) {
            Log.e(MainActivity.class.getSimpleName(),"Error: Number Format Exception!!!\tDefault value set to 1 hour.");
            // default value 1h
            timeToReset  = 3600000;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1,timeToReset);*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_REQUEST){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, getResources().getString(R.string.product_saved), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.product_not_saved), Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
