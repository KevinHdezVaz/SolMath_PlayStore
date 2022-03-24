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
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.solumath.calculator.R;
import com.solumath.calculator.activities.base.BasseEvaluatorActivity;
import com.solumath.calculator.evaluator.LaTexFactory;
import com.solumath.calculator.evaluator.MathEvaluator;
import com.solumath.calculator.evaluator.thread.Command;
import com.solumath.ncalc.calculator.BasicCalculatorActivity;
import com.solumath.ncalc.utils.DLog;
import com.gx.common.collect.Lists;

import org.matheclipse.core.interfaces.IExpr;

import java.util.ArrayList;

/**
 * Created by Duy on 06-Jan-17.
 */

public class PiActivity extends BasseEvaluatorActivity {
    private static final String STARTED = PiActivity.class.getName() + "started";
    private boolean isDataNull = true;
    private String precision = "";
    Button boton,boton2,boton3,boton4,btn_pasos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.pi_number));

        mBtnEvaluate.setText(R.string.eval);
        mHint1.setHint(getString(R.string.precision_));
        mInputFormula.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_SIGNED);

        getIntentData();
        boton2 = findViewById(R.id.btn11);

        boton3 = findViewById(R.id.btn12);
        boton4 = findViewById(R.id.btn13);
        btn_pasos = findViewById(R.id.btn_pasos);
        botonpasos.setVisibility(View.GONE);
        texto.setText("El número Pi (π) se obtiene al dividir la longitud de una circunferencia por su diámetro. Además, es un número irracional. Esto quiere decir que tiene una infinita cantidad de dígitos que se prolongan tras la coma. Por lo que, jamás se repite un mismo patrón.");
        texto2.setText("En la parte de arriba escribe los digitos que gustes para calcular la presición del número PI.");

        boton2.setVisibility(View.INVISIBLE);
        boton3.setVisibility(View.INVISIBLE);
        boton4.setVisibility(View.INVISIBLE);
        boolean isStarted = mPreferences.getBoolean(STARTED, false);
        if ((!isStarted || DLog.UI_TESTING_MODE) && isDataNull) {
            mInputFormula.setText("1000");
        }

    }

    @Override
    public void clickHelp() {

    }

    private void setPrecision(String precision) {
        this.precision = precision;
    }

    private String getPrecision() {
        return this.precision;
    }

    /**
     * get data from activity start it
     */
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
    protected String getExpression() {
        setPrecision(mInputFormula.getCleanText());
        try {
            if (Integer.parseInt(getPrecision()) <= 16) {
                return "N(Pi, 18)";
            }
        } catch (NumberFormatException nfex) {
            return "N(Pi, 18)";
        }
        return "N(Pi," + getPrecision() + ")";
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                IExpr iExpr = MathEvaluator.getInstance().evalStr(input);
                String result = LaTexFactory.toLaTeX(iExpr);
                try {
                    if (Integer.parseInt(getPrecision()) <= 16) {
                        result = result.substring(0, Integer.parseInt(getPrecision()) + 3) + "$$";
                    }
                } catch (NumberFormatException nfex) {
                }
                return Lists.newArrayList(result);
            }
        };
    }
}
