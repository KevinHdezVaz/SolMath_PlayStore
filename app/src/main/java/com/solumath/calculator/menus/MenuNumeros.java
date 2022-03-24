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
import com.solumath.calculator.symja.activities.FactorPrimeActivity;
import com.solumath.calculator.symja.activities.NumberActivity;
import com.solumath.calculator.symja.activities.PiActivity;

import java.util.ArrayList;

public class MenuNumeros extends AppCompatActivity {

    protected final Handler handler = new Handler();
    private InterstitialAd mInterstitialAd;

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

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MenuNumeros.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
            if(i == 3 ){
                Intent intent3 = new Intent(getApplicationContext(), NumberActivity.class);
                intent3.putExtra(NumberActivity.DATA, NumberActivity.NumberType.CATALAN);
                postStartActivity(intent3);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MenuNumeros.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

            }


        });


    }  private void postStartActivity(final Intent intent) {
        handler.postDelayed(() -> startActivity(intent), 100);
    }
}

