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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;


import com.solumath.calculator.R;
import com.solumath.calculator.activities.base.BasseEvaluatorActivity;
import com.solumath.calculator.evaluator.EvaluateConfig;
import com.solumath.calculator.evaluator.MathEvaluator;
import com.solumath.calculator.evaluator.thread.Command;
import com.solumath.calculator.symja.models.NumberIntegerItem;
import com.gx.common.collect.Lists;

import java.util.ArrayList;

import static com.solumath.calculator.symja.activities.NumberActivity.NumberType.CATALAN_FUNCTION;

/**
 * Number activity
 * Created by Duy on 15-Feb-17.
 */

public class NumberActivity extends BasseEvaluatorActivity {
    public static final String DATA = "DATA";
    private int type;
    Button boton,boton2,boton3,boton4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boton2 = findViewById(R.id.btn11);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            type = intent.getIntExtra(DATA, NumberType.CATALAN);
        }
        setup();
        mInputFormula.setTextColor(getResources().getColor(R.color.black));

        mInputFormula.setEnabled(false);
    }


    private void setup() {
        mBtnEvaluate.setText(R.string.eval);
        switch (type) {
            case NumberType.CATALAN:
                mHint1.setHint(getString(R.string.catalan_desc));
                texto.setText("Los números de Catalan forman una secuencia de números naturales que aparece en varios problemas de conteo que habitualmente son recursivos. Obtienen su nombre del matemático belga Eugène Charles Catalan.");
                    texto2.setText("");
                botonpasos.setVisibility(View.INVISIBLE);

                boton2.setVisibility(View.INVISIBLE);
                boton4.setVisibility(View.INVISIBLE);

                boton3.setVisibility(View.INVISIBLE);
                setTitle(R.string.catalan_number);
                break;
            case NumberType.FIBONACCI:
                mHint1.setHint(getString(R.string.fibo_desc));
                setTitle(R.string.fibonacci);
                break;
            case NumberType.PRIME:
                mHint1.setHint(getString(R.string.prime_desc));
                setTitle(R.string.prime);
                break;
            case NumberType.DIVISORS:
                mHint1.setHint(getString(R.string.divisors));
                botonpasos.setVisibility(View.INVISIBLE);


                boton2.setVisibility(View.INVISIBLE);
                boton4.setVisibility(View.INVISIBLE);

                boton3.setVisibility(View.INVISIBLE);

                texto.setText("Los divisores de un número natural son los números naturales que lo pueden dividir, resultando de cociente otro número natural y de resto 0.");
                texto2.setText("Un número es divisor de otro cuando lo divide exactamente.\n" +
                        "\n" +
                        "3 es divisor de 15;          15 : 3 = 5.\n"  +

                        "A los divisores también se les llama factores.");

                setTitle(R.string.divisors);
            default:
                mHint1.setHint(getString(R.string.enter_number));
                break;
        }
    }

    @Override
    protected String getExpression() {
        String number = mInputFormula.getCleanText();
        NumberIntegerItem item = new NumberIntegerItem(number);
        switch (type) {
            case NumberType.CATALAN:
                item.setFunction(CATALAN_FUNCTION);
                break;
            case NumberType.FIBONACCI:
                item.setFunction(NumberType.FIBONACCI_FUNCTION);
                break;
            case NumberType.PRIME:
                item.setFunction(NumberType.PRIME_FUNCTION);
                break;
            case NumberType.DIVISORS:
                item.setFunction(NumberType.DIVISORS_FUNCTION);
                break;
            default:
                break;
        }
        return item.getInput();
    }

    @Override
    public void clickHelp() {
        String function;
        switch (type) {
            case NumberType.CATALAN:
                function = NumberType.CATALAN_FUNCTION;
                break;
            case NumberType.FIBONACCI:
                function = (NumberType.FIBONACCI_FUNCTION);
                break;
            case NumberType.PRIME:
                function = (NumberType.PRIME_FUNCTION);
                break;
            case NumberType.DIVISORS:
                Toast.makeText(this, "Ingresa el número al cual quieres conocer sus divisores.", Toast.LENGTH_SHORT).show();
                break;
            default:
                function = "";
                break;
        }

    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        EvaluateConfig.loadFromSetting(getApplicationContext())
                                .setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }

    /**
     * Created by Duy on 15-Feb-17.
     */

    public static class NumberType {
        public static final int PRIME = 1;
        public static final int PRIME_Q = 2;
        public static final int CO_PRIME_Q = 3;
        public static final int FACTOR_PRIME = 4;
        public static final int CATALAN = 5;
        public static final int FIBONACCI = 6;
        public static final int LCM = 7;
        public static final int GCD = 8;
        public static final int DIVISORS = 9;

        public static final String PRIME_FUNCTION = "Prime";
        public static final String CATALAN_FUNCTION = "CatalanNumber";
        public static final String FIBONACCI_FUNCTION = "Fibonacci";
        public static final String DIVISORS_FUNCTION = "Divisors";
    }
}
