/*
 * Copyright (C) 2018 Duy Tran Le
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.solumath.calculator.symja.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.solumath.calculator.R;

import com.solumath.calculator.activities.base.BasseEvaluatorActivity;
import com.solumath.calculator.evaluator.MathEvaluator;
import com.solumath.calculator.evaluator.thread.Command;
import com.solumath.ncalc.calculator.BasicCalculatorActivity;
import com.solumath.ncalc.utils.DLog;
import com.gx.common.collect.Lists;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
     * Created by Duy on 06-Jan-17.
     */

public class FactorPrimeActivity extends BasseEvaluatorActivity {
    private static final String STARTED = FactorPrimeActivity.class.getName() + "started";
    private boolean isDataNull = true;
    Dialog epicDialog,epicDialog2;
    ImageView Animacion2;
    Button salir,seguir;
    PhotoViewAttacher photo,poto2;
    Button boton,boton2,boton3,boton4,btn_pasos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.factor_prime));

        mBtnEvaluate.setText(R.string.factor);
        mHint1.setHint(getString(R.string.input_number));
        mInputFormula.setTextColor(getResources().getColor(R.color.black));
        boton2 = findViewById(R.id.btn11);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);

        boton2.setVisibility(View.INVISIBLE);
        boton3.setVisibility(View.INVISIBLE);
        boton4.setVisibility(View.INVISIBLE);
        btn_pasos = findViewById(R.id.btn_pasos);
        botonpasos.setVisibility(View.GONE);
        getIntentData();

        texto.setText("Los factores primos de un número entero son los números primos divisores exactos de ese número entero. El proceso de búsqueda de esos divisores se denomina factorización de enteros, o factorización en números primos.");
        texto2.setText("Ejemplo:\n-Los factores primos de 6 son 2 y 3 (6 = 2 x 3). Ambos tienen multiplicidad 1.\n" +
                "-5 solo tiene un factor primo: él mismo (ya que 5 es primo). Tiene una multiplicidad 1.");


        boolean isStarted = mPreferences.getBoolean(STARTED, false);
        if ((!isStarted || DLog.UI_TESTING_MODE) && isDataNull) {
            mInputFormula.setText("102013124");
        }

    }

    @Override
    public void clickHelp() {
        mostrarSalir();
    }
    public void mostrarSalir() {
        epicDialog2 = new Dialog(this);
        epicDialog2.setContentView(R.layout.about);

        Animacion2 = epicDialog2.findViewById(R.id.Animacion2);

        photo = new PhotoViewAttacher(Animacion2);
        seguir = (Button) epicDialog2.findViewById(R.id.botonvamo);



        seguir.setOnClickListener(view -> {

               epicDialog2.dismiss();


        });

        epicDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        epicDialog2.show();


    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                isDataNull = false;
                clickEvaluate();
            }
        }
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                String fraction = MathEvaluator.getInstance().factorPrime(input);
                return Lists.newArrayList(fraction);
            }
        };
    }
}
