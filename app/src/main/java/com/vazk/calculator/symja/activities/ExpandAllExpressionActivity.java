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
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.vazk.calculator.R;

import com.vazk.calculator.activities.base.BaseEvaluatorActivity;
import com.vazk.calculator.evaluator.EvaluateConfig;
import com.vazk.calculator.evaluator.MathEvaluator;
import com.vazk.calculator.evaluator.thread.Command;
import com.vazk.calculator.symja.tokenizer.ExpressionTokenizer;
import com.vazk.ncalc.calculator.BasicCalculatorActivity;
import com.vazk.ncalc.utils.DLog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

/**
 * Created by Duy on 19/7/2016
 */
public class ExpandAllExpressionActivity extends BaseEvaluatorActivity {
    private static final String STARTED = ExpandAllExpressionActivity.class.getName() + "started";
    SharedPreferences preferences;
    private boolean isDataNull = true;

    Button boton,boton2,boton3,boton4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.expand));
        mHint1.setHint(getString(R.string.enter_expression));
        mBtnEvaluate.setText(R.string.expand);
        getIntentData();

        texto.setText("El binomio de Newton es la fórmula que nos permite hallar las potencias de un binomio.\n"
                );

        texto2.setText("El teorema binomial o binomio de Newton especifica la expansión de cualquier potencia de un binomio, es decir, la expansión de  (a+b)^m");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isStarted = preferences.getBoolean(STARTED, false);
        if ((!isStarted || DLog.UI_TESTING_MODE) && isDataNull) {
            mInputFormula.setText("(x + 2a)^8");
        }
        if(isFirstTime()){
            clickHelp();
        }
        boton2 = findViewById(R.id.btn11);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);
        boton3.setText(Html.fromHtml("(x-y)<sup>4</sup>"));
        boton2.setText(Html.fromHtml("(x+y)<sup>2</sup>"));

        boton4.setVisibility(View.INVISIBLE);
        mInputFormula.setTextColor(getResources().getColor(R.color.black));

        mInputFormula.setEnabled(false);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputFormula.setText("(x+y)^2");


            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputFormula.setText("(x-y)^4");


            }
        });
    }

    @Override
    public void clickHelp() {
 
        final SharedPreferences.Editor editor = preferences.edit();
        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.enter_expression),
                getString(R.string.input_expand_here));
        target0.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target = TapTarget.forView(mBtnEvaluate,
                getString(R.string.expand),
                getString(R.string.push_expand_button));
        target.drawShadow(true)
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
        TapTargetSequence sequence = new TapTargetSequence(ExpandAllExpressionActivity.this);
        sequence.targets(target0, target,target1);
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

    @Override
    protected String getExpression() {
        String expr = mInputFormula.getCleanText();
        return "ExpandAll(" + expr + ")";
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {

                EvaluateConfig config = EvaluateConfig.loadFromSetting(ExpandAllExpressionActivity.this);
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));

                String decimal = MathEvaluator.getInstance().derivativeFunction(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL));

                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }  private boolean isFirstTime()
    {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("shibu", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("shibu", true);
            editor.commit();
        }
        return !ranBefore;
    }


    /**
     * get data from another (eg. clipboard)
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                data = new ExpressionTokenizer().getNormalExpression(data);
                isDataNull = false;
                clickEvaluate();
            }
        }
    }

}
