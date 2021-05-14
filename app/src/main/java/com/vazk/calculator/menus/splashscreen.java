package com.vazk.calculator.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.wave.MultiWaveHeader;
import com.vazk.calculator.PrincipalMenu;
import com.vazk.calculator.R;

public class splashscreen extends AppCompatActivity {
    ImageView imagen;
    TextView texto,chacos;
    MediaPlayer mediaPlayer;


    RelativeLayout fondorelativo;
    MultiWaveHeader wave_header,wave_header2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);




        imagen = findViewById(R.id.chido);
        texto = findViewById(R.id.chacos);

        //sonido en los botones
        mediaPlayer = MediaPlayer.create(this, R.raw.intro);
        mediaPlayer.start();
         chacos = findViewById(R.id.chacos);

        wave_header = findViewById(R.id.wave_header);
        wave_header2 = findViewById(R.id.wave_header2);
        fondorelativo = findViewById(R.id.fondorelativo);


        imagen.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_2));
        texto.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_left_time_trials));

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(splashscreen.this, PrincipalMenu.class);
                startActivity(intent);
                finish();
            };
        }, 3500);

    }



    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
