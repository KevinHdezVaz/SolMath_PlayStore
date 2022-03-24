package com.solumath.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mrntlu.toastie.Toastie;

// TODO: Import classes from Analytics Kit.


public class SettingActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText editFavorSport;
    private String strFavorSport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        btnSave = (Button) findViewById(R.id.save_setting_button);
        editFavorSport = (EditText) findViewById(R.id.edit_favorite_sport);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                strFavorSport = editFavorSport.getText().toString().trim();


                Toastie.success(getApplicationContext(),"Gracias!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingActivity.this, PrincipalMenu.class));
                finish();
            }
        });
    }
}
