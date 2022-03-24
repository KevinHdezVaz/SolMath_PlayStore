package com.solumath.calculator.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.solumath.calculator.R;
import com.solumath.calculator.adaptadores.CustomAdapter;
import com.solumath.calculator.adaptadores.GridView_adapter;
import com.solumath.calculator.symja.activities.PermutationActivity;

import java.util.ArrayList;

public class MenuEstadistica extends AppCompatActivity {
    protected final Handler handler = new Handler();

    private CustomAdapter adaptador;
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_estadistica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        toolbar.setNavigationOnClickListener(v -> {

            finish();
        });






        grid = (GridView)  findViewById(R.id.item);

        final ArrayList<GridView_adapter> listItems = new ArrayList<>();
        listItems.add(new GridView_adapter(R.drawable.ceta,getString(R.string.combination)));
        listItems.add(new GridView_adapter((R.drawable.pags), getString(R.string.permutation)));






        adaptador = new CustomAdapter(this, listItems);
        grid.setAdapter(adaptador);

        grid.setOnItemClickListener((adapterView, view, i, l) -> {

            if(i == 0 ){
               Intent  intent = new Intent(getApplicationContext(), PermutationActivity.class);
                intent.putExtra(PermutationActivity.TYPE_NUMBER, PermutationActivity.TYPE_COMBINATION);
                postStartActivity(intent);
            }
            if(i == 1 ){
                Intent intent = new Intent(getApplicationContext(), PermutationActivity.class);
                intent.putExtra(PermutationActivity.TYPE_NUMBER, PermutationActivity.TYPE_PERMUTATION);
                postStartActivity(intent);
            }


        });


    }   private void postStartActivity(final Intent intent) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 100);
    }

}