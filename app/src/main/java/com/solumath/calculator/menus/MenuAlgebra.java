package com.solumath.calculator.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.solumath.calculator.R;
import com.solumath.calculator.adaptadores.CustomAdapter;
import com.solumath.calculator.adaptadores.GridView_adapter;
import com.solumath.calculator.symja.activities.ExpandAllExpressionActivity;
import com.solumath.calculator.symja.activities.FactorExpressionActivity;
import com.solumath.calculator.symja.activities.SimplifyExpressionActivity;
import com.solumath.calculator.symja.activities.SolveEquationActivity;
import com.solumath.ncalc.calculator.BasicCalculatorActivity;
import com.solumath.ncalc.calculator.LogicCalculatorActivity;
import com.solumath.ncalc.geom2d.GeometryDescartesActivity;
import com.solumath.ncalc.matrix.MatrixCalculatorActivity;
import com.solumath.ncalc.systemequations.SystemEquationActivity;
import com.solumath.ncalc.unitconverter.UnitCategoryActivity;

import java.util.ArrayList;

public class MenuAlgebra extends AppCompatActivity {

ImageButton boton1,boton2,imagen;
    ImageView cerrar ;
    Button cerrar2;
    Dialog epicDialog;
    private CustomAdapter adaptador;
    private GridView grid;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_algebra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
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


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }




        /*
        WisePlayer player = VideoKitApplication.getWisePlayerFactory().createWisePlayer();
        player.setVideoType(PlayerConstants.PlayMode.PLAY_MODE_NORMAL);
        player.setBookmark(10000);
        player.setCycleMode(PlayerConstants.CycleMode.MODE_CYCLE);
        player.setPlayUrl("https://videoplay-mos-dra.dbankcdn.com/P_VT/video_injection/92/v3/C072F990370950198572111872/MP4Mix_H.264_1920x1080_6000_HEAAC1_PVC_NoCut.mp4");

        player.ready();
*/


        //PARA QUE APAREZCA LA FLECHA DE ATRAS
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });



        grid = (GridView)  findViewById(R.id.item);

        final ArrayList<GridView_adapter> listItems = new ArrayList<>();
        listItems.add(new GridView_adapter(R.drawable.calculadora,getString(R.string.science_calculator)));
        listItems.add(new GridView_adapter((R.drawable.calculadorr), getString(R.string.logic_calculator)));
        listItems.add(new GridView_adapter((R.drawable.ecuacion), getString(R.string.solve_equation)));
        listItems.add(new GridView_adapter((R.drawable.matriz), getString(R.string.matrix)));
         listItems.add(new GridView_adapter((R.drawable.geometria), getString(R.string.nav_descartes)));
        listItems.add(new GridView_adapter((R.drawable.reducir), getString(R.string.simplify_expression)));
        listItems.add(new GridView_adapter((R.drawable.pizarron), getString(R.string.factor_polynomial)));
        listItems.add(new GridView_adapter((R.drawable.calculador), getString(R.string.all_unit_converter)));
        listItems.add(new GridView_adapter((R.drawable.pensar), getString(R.string.binomial_newton)));
        listItems.add(new GridView_adapter((R.drawable.sistem), getString(R.string.linear_system)));




        adaptador = new CustomAdapter(this, listItems);
        grid.setAdapter(adaptador);

       grid.setOnItemClickListener((adapterView, view, i, l) -> {

           if(i == 0 ){
               startActivity(new Intent(MenuAlgebra.this, BasicCalculatorActivity.class));
               if (mInterstitialAd != null) {
                   mInterstitialAd.show(MenuAlgebra.this);
               } else {
                   Log.d("TAG", "The interstitial ad wasn't ready yet.");
               }
            }
           if(i == 1 ){
               startActivity(new Intent(MenuAlgebra.this, LogicCalculatorActivity.class));
            }
           if(i == 2 ){
              startActivity(new Intent(MenuAlgebra.this, SolveEquationActivity.class));


               if (mInterstitialAd != null) {
                   mInterstitialAd.show(MenuAlgebra.this);
               } else {
                   Log.d("TAG", "The interstitial ad wasn't ready yet.");
               }
           }


           if(i == 3 ){
               startActivity(new Intent(MenuAlgebra.this, MatrixCalculatorActivity.class));


           }

           if(i == 4 ){
               startActivity(new Intent(MenuAlgebra.this, GeometryDescartesActivity.class));
           }

           if(i == 5){
               startActivity(new Intent(MenuAlgebra.this, SimplifyExpressionActivity.class));
           }
           if(i == 6){
               startActivity(new Intent(MenuAlgebra.this, FactorExpressionActivity.class));



           }
           if(i == 7){
               startActivity(new Intent(MenuAlgebra.this, UnitCategoryActivity.class));



           }
           if(i == 8){
               startActivity(new Intent(MenuAlgebra.this, ExpandAllExpressionActivity.class));
           }
           if(i == 9){
               startActivity(new Intent(MenuAlgebra.this, SystemEquationActivity.class));
               if (mInterstitialAd != null) {
                   mInterstitialAd.show(MenuAlgebra.this);
               } else {
                   Log.d("TAG", "The interstitial ad wasn't ready yet.");
               }

           }
       });


    }

    public void mostrarInfo(){

        epicDialog = new Dialog(this);
        epicDialog.setContentView(R.layout.ayuda);
        cerrar = (ImageView) epicDialog.findViewById(R.id.cerrarVentana);
        cerrar2 = (Button) epicDialog.findViewById(R.id.botonvamo);

        cerrar.setOnClickListener(view -> epicDialog.dismiss());
        cerrar2.setOnClickListener(view -> epicDialog.dismiss());

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();



    }


}



