package com.vazk.calculator.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.vazk.calculator.R;
import com.vazk.calculator.adaptadores.CustomAdapter;
import com.vazk.calculator.adaptadores.GridView_adapter;
import com.vazk.calculator.symja.activities.DerivativeActivity;
import com.vazk.calculator.symja.activities.FactorPrimeActivity;
import com.vazk.calculator.symja.activities.IntegrateActivity;
import com.vazk.calculator.symja.activities.LimitActivity;
import com.vazk.calculator.symja.activities.ModuleActivity;
import com.vazk.calculator.symja.activities.NumberActivity;
import com.vazk.calculator.symja.activities.PiActivity;
import com.vazk.calculator.symja.activities.PrimitiveActivity;

import java.util.ArrayList;

public class MenuNumeros extends AppCompatActivity {

    protected final Handler handler = new Handler();

    private CustomAdapter adaptador;
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_numeros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

       
        toolbar.setNavigationOnClickListener(v -> {
            //regresar...
            finish();
        });



        grid = (GridView)  findViewById(R.id.item);

        final ArrayList<GridView_adapter> listItems = new ArrayList<>();
        listItems.add(new GridView_adapter(R.drawable.numeros,getString(R.string.factor_prime)));
        listItems.add(new GridView_adapter((R.drawable.pii), getString(R.string.pi_number)));


        listItems.add(new GridView_adapter((R.drawable.lok), getString(R.string.divisors)));
        listItems.add(new GridView_adapter((R.drawable.integral), getString(R.string.catalan_number)));
    





        adaptador = new CustomAdapter(this, listItems);
        grid.setAdapter(adaptador);

        grid.setOnItemClickListener((adapterView, view, i, l) -> {

            if(i == 0 ){
                Intent intent = new Intent(getApplicationContext(), FactorPrimeActivity.class);
                postStartActivity(intent);
            }

            if(i == 1 ){

                Intent intent6 = new Intent(getApplicationContext(), PiActivity.class);
                postStartActivity(intent6);
            }

            if(i == 2 ){
                Intent intent5 = new Intent(getApplicationContext(), NumberActivity.class);
                intent5.putExtra(NumberActivity.DATA, NumberActivity.NumberType.DIVISORS);
                postStartActivity(intent5);

            }
            if(i == 3 ){
                Intent intent3 = new Intent(getApplicationContext(), NumberActivity.class);
                intent3.putExtra(NumberActivity.DATA, NumberActivity.NumberType.CATALAN);
                postStartActivity(intent3);


            }


        });


    }  private void postStartActivity(final Intent intent) {
        handler.postDelayed(() -> startActivity(intent), 100);
    }
}
