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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.solumath.calculator.R;

import com.solumath.calculator.activities.base.BasseEvaluatorActivity;
import com.solumath.calculator.evaluator.EvaluateConfig;
import com.solumath.calculator.evaluator.MathEvaluator;
import com.solumath.calculator.evaluator.thread.Command;
import com.solumath.calculator.symja.tokenizer.ExpressionTokenizer;
import com.solumath.ncalc.calculator.BasicCalculatorActivity;
import com.solumath.ncalc.utils.DLog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.gx.common.collect.Lists;

import java.util.ArrayList;

/**
 * Created by Duy on 19/7/2016
 */
public class FactorExpressionActivity extends BasseEvaluatorActivity {

    private static final String STARTED = FactorExpressionActivity.class.getName() + "started";
    private final static String TAG = FactorExpressionActivity.class.getSimpleName();
    SharedPreferences preferences;
    private boolean isDataNull = true;
    Button boton,boton2,boton3,boton4,btn_pasos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.factor));

        mBtnEvaluate.setText(R.string.factor);
        mHint1.setHint(getString(R.string.enter_expression));
        getIntentData();

        texto.setText("El proceso de factorización es esencial para la simplificación de muchas expresiones algebraicas y es una herramienta útil para resolver ecuaciones de grado superior. De hecho, el proceso de factorización es tan importante que muy poco de álgebra más allá de este punto se puede lograr sin comprenderlo.");
        texto2.setText("Ejemplo: \n\n" +
                "x^2+5x+4" +
                "\n*Resultado:\n(x+1)(x+4) ");
        boton2 = findViewById(R.id.btn11);
        btn_pasos = findViewById(R.id.btn_pasos);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);
botonpasos.setVisibility(View.GONE);
        boton2.setText(Html.fromHtml("2x<sup>4</sup> +4x<sup>2</sup>"));

         boton3.setText(Html.fromHtml("x<sup>2</sup> +5X+4"));
        boton4.setText(Html.fromHtml("3x<sup>2</sup>+7-6"));
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInputFormula.setText("2x^4+4x^2");

            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputFormula.setText("x^2+5x+4");


            }
        });
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputFormula.setText("3x^2+7-6");


            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isStarted = preferences.getBoolean(STARTED, false);
        if (!isStarted || DLog.UI_TESTING_MODE) {
            if (isDataNull) {
                mInputFormula.setText("x^4 - 1");
            }
        }

        if (isFirstTime()){
            clickHelp();
        }


        mInputFormula.setTextColor(getResources().getColor(R.color.black));

        mInputFormula.setEnabled(false);
    }

    @Override
    public void clickHelp() {
        final SharedPreferences.Editor editor = preferences.edit();
        //if is not start

        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.enter_expression),
                getString(R.string.input_analyze_here))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target = TapTarget.forView(mBtnEvaluate,
                getString(R.string.factor_polynomial),
                getString(R.string.push_analyze_button))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);
        TapTarget target1 = TapTarget.forView(botonpasos,
                "Mostrar Pasos",
                "Despues de escribir la ecuación puedes ver los pasos de la misma." )
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);
        TapTargetSequence sequence = new TapTargetSequence(FactorExpressionActivity.this);
        sequence.targets(target0, target1,target);
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                editor.putBoolean(STARTED, true);
                editor.apply();
                clickEvaluate();
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {

            }
        });
         sequence.start();
    }

    private boolean isFirstTime()
    {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("kevin", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("kevin", true);
            editor.commit();
        }
        return !ranBefore;
    }
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                data = new ExpressionTokenizer().getNormalExpression(data);
                mInputFormula.setText(data);
                isDataNull = false;
                clickEvaluate();
            }
        }
    }

    @Override
    protected String getExpression() {
        return mInputFormula.getCleanText();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {

                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().factorPolynomial(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }


}
