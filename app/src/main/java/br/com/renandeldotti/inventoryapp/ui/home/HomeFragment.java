package br.com.renandeldotti.inventoryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import br.com.renandeldotti.inventoryapp.R;

public class HomeFragment extends Fragment {

    private Button buttonShow;
    private boolean isButtonShowVisible = false;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        buttonShow = root.findViewById(R.id.home_buttonShow);

        root.findViewById(R.id.ritmo_vendas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonShowVisible){
                    buttonShow.setVisibility(View.GONE);
                    isButtonShowVisible = false;
                }else {
                    buttonShow.setVisibility(View.VISIBLE);
                    isButtonShowVisible = true;
                }
            }
        });
        root.findViewById(R.id.aumento_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.queda_vendas).setOnClickListener(clickListener);
        root.findViewById(R.id.total_vendas).setOnClickListener(clickListener);


        return root;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Todo: Criar tela...", Toast.LENGTH_SHORT).show();
        }
    };
}
