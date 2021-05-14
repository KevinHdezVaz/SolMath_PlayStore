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

package com.vazk.calculator.symja.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.WorkerThread;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vazk.calculator.R;
import com.vazk.calculator.activities.base.BaseEvaluatorActivity;
import com.vazk.calculator.evaluator.EvaluateConfig;
import com.vazk.calculator.evaluator.MathEvaluator;
import com.vazk.calculator.evaluator.thread.Command;
import com.vazk.calculator.symja.models.SolveItem;
import com.vazk.calculator.symja.tokenizer.ExpressionTokenizer;
import com.vazk.ncalc.calculator.BasicCalculatorActivity;
import com.vazk.ncalc.utils.DLog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

import static com.vazk.calculator.R.string.solve;


public class SolveEquationActivity extends BaseEvaluatorActivity
        implements View.OnClickListener {
    private static final String STARTED = SolveEquationActivity.class.getName() + "started";
    public TextView texto;
    public TextView texto2;
    protected SharedPreferences preferences;
    private boolean isDataNull = true;
    Button boton,boton2,boton3,boton4;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.solve_equation);
        mHint1.setHint(getString(R.string.input_equation));

        mBtnEvaluate.setText(solve);
        getIntentData();
        texto = findViewById(R.id.texto);
        texto2 = findViewById(R.id.texto2);
        boton2 = findViewById(R.id.btn11);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);

        boton2.setText("-5x - 6 = 3x - 8");
        boton3.setText(Html.fromHtml("-X<sup>2</sup> - x - 6 = 0"));


        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInputFormula.setText("-5x-6=3x-8");

            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputFormula.setText("-x^2-x-6=0");


            }
        });


        texto.setText("Esta calculadora de ecuaciones permite resolver una ecuación online en forma exacta con los pasos del cálculo: ecuación de primer grado, ecuación de segundo grado, ecuación de producto cero, ecuación logarítmica, ecuación diferencial.");
        texto2.setText("EjemploS: ");
            Html.fromHtml("x<sup>2</sup>");

        // \n\n-5x-6=3x-8\n-x^2-x-6=0\n-x^4-5x^2+4=0"
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isStarted = preferences.getBoolean(STARTED, false);

        if ((!isStarted || DLog.UI_TESTING_MODE) && isDataNull) {
            mInputFormula.setText("243");

        }


        mInputFormula.setTextColor(getResources().getColor(R.color.black));

        mInputFormula.setEnabled(false);

        if(isFirstTime()){
            clickHelp();

        }
    }

    @Override
    public void clickHelp() {
        final SharedPreferences.Editor editor = preferences.edit();
        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.input_equation),
                getString(R.string.input_equation_here))
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

        TapTarget target = TapTarget.forView(mBtnEvaluate,
                getString(R.string.solve_equation),
                getString(R.string.push_solve_button))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);




        TapTargetSequence sequence = new TapTargetSequence(SolveEquationActivity.this);
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
                clickEvaluate();
            }
        });
         sequence.start();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                data = new ExpressionTokenizer().getNormalExpression(data);
                isDataNull = false;


                if (!data.isEmpty()) {
                    clickEvaluate();
                } else {
                    //
                }
            }
        }
    }  private boolean isFirstTime()
    {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("game", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("game", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    protected String getExpression() {
        String expr = mInputFormula.getCleanText();
        SolveItem solveItem = new SolveItem(expr);
        return solveItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().solveEquation(input,
                        config.setEvalMode(EvaluateConfig.FRACTION), SolveEquationActivity.this);

                String decimal = MathEvaluator.getInstance().solveEquation(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL),  SolveEquationActivity.this);


                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }

}
