package com.vazk.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.vazk.calculator.menus.MenuAlgebra;
import com.vazk.calculator.menus.menuCalculo;

public class PrincipalMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView nav;
    MenuItem tools, home,home2 ;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView imagen1,imagen2,imagen3,imagen4,imagen5;
    ImageButton btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);


        nav=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        tools =  nav.getMenu().findItem(R.id.home);
        home2 =  nav.getMenu().findItem(R.id.nav_acerca);
        btn1 = (ImageButton) findViewById(R.id.hola);
        btn2 = (ImageButton) findViewById(R.id.hola2);
        imagen1 = findViewById(R.id.animacion_1);
        imagen2 = findViewById(R.id.animacion_2);
        imagen3 = findViewById(R.id.animacion_3);
        imagen4 = findViewById(R.id.animacion_4);








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



        drawerLayout=findViewById(R.id.drawer_layout);

        btn1.setOnClickListener(view -> {
             Intent intent = new Intent(PrincipalMenu.this, MenuAlgebra.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(PrincipalMenu.this, menuCalculo.class);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}