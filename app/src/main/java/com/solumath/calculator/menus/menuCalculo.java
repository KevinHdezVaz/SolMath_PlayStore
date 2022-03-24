package com.solumath.calculator.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.solumath.calculator.R;
import com.solumath.calculator.adaptadores.CustomAdapter;
import com.solumath.calculator.adaptadores.GridView_adapter;
import com.solumath.calculator.symja.activities.DerivativeActivity;
import com.solumath.calculator.symja.activities.IntegrateActivity;
import com.solumath.calculator.symja.activities.LimitActivity;
import com.solumath.calculator.symja.activities.PrimitiveActivity;

import java.util.ArrayList;

public class menuCalculo extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private CustomAdapter adaptador;
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //PARA QUE APAREZCA LA FLECHA DE ATRAS
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.codigoAd), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("S", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("S", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });




        grid = (GridView)  findViewById(R.id.item);

        final ArrayList<GridView_adapter> listItems = new ArrayList<>();
        listItems.add(new GridView_adapter(R.drawable.funcion,getString(R.string.derivative)));
        listItems.add(new GridView_adapter((R.drawable.ic_primitive_black), getString(R.string.primitive)));
        listItems.add(new GridView_adapter((R.drawable.integral), getString(R.string.integrate)));
        listItems.add(new GridView_adapter((R.drawable.lok), getString(R.string.limit)));





        adaptador = new CustomAdapter(this, listItems);
        grid.setAdapter(adaptador);

        grid.setOnItemClickListener((adapterView, view, i, l) -> {

            if(i == 0 ){
                startActivity(new Intent(menuCalculo.this, DerivativeActivity.class));
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(menuCalculo.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
            if(i == 1 ){
                startActivity(new Intent(menuCalculo.this, PrimitiveActivity.class));
            }
            if(i == 2 ){
                startActivity(new Intent(menuCalculo.this, IntegrateActivity.class));

            }
            if(i == 3 ){
                startActivity(new Intent(menuCalculo.this, LimitActivity.class));
             }

        });


    }

}
