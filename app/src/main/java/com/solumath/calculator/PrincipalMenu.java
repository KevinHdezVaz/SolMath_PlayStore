package com.solumath.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
 import com.solumath.calculator.menus.MenuAlgebra;
import com.solumath.calculator.menus.MenuEstadistica;
import com.solumath.calculator.menus.MenuNumeros;
import com.solumath.calculator.menus.menuCalculo;

import java.util.HashSet;
import java.util.Set;

public class PrincipalMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView nav;
    MenuItem tools, home,home2 ,home3,home4,home5;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView imagen1,imagen2,imagen3,imagen4,imagen5;
    ImageButton btn1, btn2, btn3, btn4;
    LottieAnimationView animationView,animationView2;
     ImageButton boton1,boton2,imagen,imaggen2;
    ImageView cerrar ;
    Button cerrar2,seguir;
    Dialog epicDialog,epicDialog2;
    TextView estatus;
    Boolean activado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal_menu);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        imagen = findViewById(R.id.informacion);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInfo();
            }
        });
        nav=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        tools =  nav.getMenu().findItem(R.id.home);
        home2 =  nav.getMenu().findItem(R.id.nav_acerca);
        btn1 = (ImageButton) findViewById(R.id.hola);
        btn2 = (ImageButton) findViewById(R.id.hola2);
        btn3 = (ImageButton) findViewById(R.id.hola3);
        btn4= (ImageButton) findViewById(R.id.hola4);

        imagen1 = findViewById(R.id.animacion_1);
        imagen2 = findViewById(R.id.animacion_2);
        imagen3 = findViewById(R.id.animacion_3);
        imagen4 = findViewById(R.id.animacion_4);
        animationView = findViewById(R.id.animationView);
        animationView2 = findViewById(R.id.animationView2);

        animationView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_arriba));
        animationView2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_abajo));












        imagen1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_abajo));
        imagen2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_left_quick_math));
        imagen3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_0));
        imagen4.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_arriba));




        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

         home3 =  nav.getMenu().findItem(R.id.nav_share);
        home4 =  nav.getMenu().findItem(R.id.nav_profile);



        drawerLayout=findViewById(R.id.drawer_layout);

        btn1.setOnClickListener(view -> {
             Intent intent = new Intent(PrincipalMenu.this, MenuAlgebra.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(PrincipalMenu.this, menuCalculo.class);
            startActivity(intent);
        });

        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(PrincipalMenu.this, MenuEstadistica.class);
            startActivity(intent);
        });


        btn4.setOnClickListener(view -> {
            Intent intent = new Intent(PrincipalMenu.this, MenuNumeros.class);
            startActivity(intent);
        });



    }


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }



    public void mostrarInfo(){

        epicDialog = new Dialog(this);
        epicDialog.setContentView(R.layout.ayuda);
        cerrar = (ImageView) epicDialog.findViewById(R.id.cerrarVentana);
        cerrar2 = (Button) epicDialog.findViewById(R.id.btnCLick);
        estatus = (TextView)epicDialog.findViewById(R.id.estadoVar);
        cerrar.setOnClickListener(view -> epicDialog.dismiss());

        cerrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.solumath.calculator";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

         epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();






    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_acerca:
                mostrarSalir();
                break;

            case R.id.nav_share:


                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "SoluMath - Aprende todo en uno. \nApp completa con todo lo que un estudiante necesita, un pack completo!";
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Aplicación Todo en uno");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir Via"));
                break;



            case R.id.nav_profile:

                String url = "https://play.google.com/store/apps/details?id=com.app.formutodo&hl=es_MX&gl=US";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }

    public void mostrarSalir() {
        epicDialog2 = new Dialog(this);
        epicDialog2.setContentView(R.layout.about2);

        seguir = (Button) epicDialog2.findViewById(R.id.botonvamo);



        seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              epicDialog2.dismiss();



            }
        });

        epicDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        epicDialog2.show();


    }

}