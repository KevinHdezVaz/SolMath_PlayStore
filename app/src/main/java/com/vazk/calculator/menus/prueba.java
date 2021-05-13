package com.vazk.calculator.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.mrntlu.toastie.Toastie;
import com.vazk.calculator.R;

public class prueba extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    WebView miVisorWeb;
    String url = "https://kevinhdezvaz.github.io/agarra/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);



        miVisorWeb = (WebView) findViewById(R.id.visorWeb);
        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        miVisorWeb.loadUrl(url);
        lottieAnimationView = findViewById(R.id.animationView22);


         miVisorWeb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //elimina el gif.
             lottieAnimationView.setVisibility(View.INVISIBLE);
             lottieAnimationView.cancelAnimation();
//que soolo pase una vez
                isFirstTime();



            }
        });
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("asdd", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("asdd", true);
            editor.commit();
            Toastie.success(prueba.this,"Pega la ecuación en la entrada de datos de arriba para mostrar los pasos.",Toast.LENGTH_LONG).show();

        }
        return !ranBefore;
    }

}