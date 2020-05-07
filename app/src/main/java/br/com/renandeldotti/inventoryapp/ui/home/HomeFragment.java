package br.com.renandeldotti.inventoryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;

import br.com.renandeldotti.inventoryapp.R;

public class HomeFragment extends Fragment {

    private View tvRitmoVendas;
    private int isTvRitmoVendasVisible;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvRitmoVendas = root.findViewById(R.id.home_rateSales_hidden);
        isTvRitmoVendasVisible = tvRitmoVendas.getVisibility();

        TextView homeSales = root.findViewById(R.id.home_todaySales_value);
        TextView weekSales = root.findViewById(R.id.home_weekSales_value);
        TextView monthSales = root.findViewById(R.id.home_monthSales_value);

        int i = new Random().nextInt(100);

        homeSales.setText((i >= 60)?"5":"6");
        weekSales.setText((i <= 40)?"50":"60");
        monthSales.setText((i > 40 && i < 60)?"500"+i:"600"+i);

        root.findViewById(R.id.ritmo_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTvRitmoVendasVisible == View.VISIBLE){
                    tvRitmoVendas.setVisibility(View.GONE);
                    isTvRitmoVendasVisible = View.GONE;
                }else {
                    tvRitmoVendas.setVisibility(View.VISIBLE);
                    isTvRitmoVendasVisible = View.VISIBLE;
                }
            }
        });


        root.findViewById(R.id.aumento_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.queda_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.total_vendas).setOnClickListener(clickListener);


        return root;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Todo: Criar tela...", Toast.LENGTH_SHORT).show();
        }
    };
}
