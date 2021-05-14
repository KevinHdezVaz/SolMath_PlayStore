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
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;


import com.vazk.calculator.R;
import com.vazk.calculator.activities.base.BaseEvaluatorActivity;
import com.vazk.calculator.evaluator.EvaluateConfig;
import com.vazk.calculator.evaluator.MathEvaluator;
import com.vazk.calculator.evaluator.thread.Command;
import com.vazk.calculator.symja.models.SimplifyItem;
import com.vazk.ncalc.calculator.BasicCalculatorActivity;
import com.vazk.ncalc.utils.DLog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

/**
 * Created by Duy on 19/7/2016
 */
public class SimplifyExpressionActivity extends BaseEvaluatorActivity {
    private static final String STARTED = SimplifyExpressionActivity.class.getName() + "started";
    SharedPreferences preferences;
    private boolean isDataNull = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle( getString(R.string.simplify_expression));
        mBtnEvaluate.setText(R.string.simplify);
        mHint1.setHint(getString(R.string.enter_expression));
        getIntentData();

        mInputFormula.setText("a - b + 2a - b");

        texto.setText("Algunas expresiones poseen dos termines o más que tienen un mismo literal (x) y dos constantes.\n" +
                "Por tanto, es posible poder abreviar o “reducir” las expresiones de manera que se pueda trabajar mas ágilmente con ellas.");
        texto2.setText("Por ejemplo: " +
                "Reducir la expresión 6x + 7 - 2x -5\n" +
                "*Se tiene\n" +
                "       6x + 7 -2x - 5\n" +
                "*Luego asociamos\n" +
                "       (6x - 2x) + (7 - 5)\n" +
                "*Finalmente, sumando o restando términos semejantes, se obtiene:\n" +
                "       4x+2");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        boolean isStarted = preferences.getBoolean(STARTED, false);
        if (!isStarted  ) {

                mInputFormula.setText("a - b + 2a - b");

        }

        if(isFirstTime()){
            clickHelp();
        }
    }

    @Override
    public void clickHelp() {
        final SharedPreferences.Editor editor = preferences.edit();

        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.enter_expression),
                getString(R.string.input_simplify_here))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);


        //if is not start
        TapTarget target = TapTarget.forView(mBtnEvaluate,
                getString(R.string.simplify_expression),
                getString(R.string.push_simplify_button))
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

        TapTargetSequence sequence = new TapTargetSequence(SimplifyExpressionActivity.this);
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

    @Override
    protected String getExpression() {
        SimplifyItem simplifyItem = new SimplifyItem(mInputFormula.getCleanText());
        return simplifyItem.getInput();
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
    private boolean isFirstTime()
    {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("hola", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hola", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));

                String decimal = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL));

                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }
}
