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

package com.vazk.calculator.activities.base;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mrntlu.toastie.Toastie;
 import com.vazk.calculator.R;
import com.vazk.calculator.evaluator.EvaluateConfig;
import com.vazk.calculator.evaluator.exceptions.ExpressionChecker;
import com.vazk.calculator.evaluator.exceptions.ParsingException;
import com.vazk.calculator.evaluator.thread.BaseThread;
import com.vazk.calculator.evaluator.thread.CalculateThread;
import com.vazk.calculator.evaluator.thread.Command;
import com.vazk.calculator.history.ResultEntry;
import com.vazk.calculator.menus.prueba;
import com.vazk.calculator.symja.activities.ResultAdapter;
import com.vazk.ncalc.document.FunctionSuggestionAdapter;
import com.vazk.ncalc.document.MarkdownDocumentActivity;
import com.vazk.ncalc.document.model.FunctionDocumentItem;
import com.vazk.ncalc.view.ResizingEditText;

import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.ArrayList;


/**
 * Abstract for eval equation, trig to exp,...
 * <p>
 * Created by Duy on 19/7/2016
 */
public abstract class BaseEvaluatorActivity extends NavDrawerActivity
        implements View.OnClickListener, FunctionSuggestionAdapter.OnSuggestionClickListener {
    protected String TAG = BaseEvaluatorActivity.class.getName();

    public LinearLayout linearLayout;
    protected EditText mEditLowerBound, mEditUpperBound;
    protected LinearLayout mLayoutLimit;
    protected TextInputLayout mHint1, mHint2;
    protected AppCompatSpinner mSpinner;
    Button boton;
     protected ResizingEditText mInputFormula;
    protected ResizingEditText mInputFormula2;
public CardView cardView;
    protected SharedPreferences mPreferences;
    protected ContentLoadingProgressBar mProgress;
    protected Button mBtnClear, mBtnEvaluate;
    public TextView texto,texto2;
    public AppCompatButton botonpasos;

    protected RecyclerView mResultView;
    private ResultAdapter mResultAdapter;
    private final View.OnKeyListener mFormulaOnKeyListener = (view, keyCode, keyEvent) -> {
        switch (keyCode) {
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
            case KeyEvent.KEYCODE_ENTER:
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    clickEvaluate();
                }
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        String input = mCalculatorSetting.getString("input_" + getClass().getSimpleName());
        mInputFormula.setText(input);
    }

    /**
     * save input
     */
    @Override
    protected void onPause() {
        super.onPause();
        mCalculatorSetting.put("input_" + getClass().getSimpleName(),
                mInputFormula.getText().toString());
    }

    private void initView() {
        mBtnEvaluate = findViewById(R.id.btn_solve);
        mInputFormula = findViewById(R.id.edit_input);
        mInputFormula.setOnSuggestionClickListener(this);
        linearLayout = findViewById(R.id.lineaCombinacion);
         mProgress = findViewById(R.id.progress_bar);
        mSpinner = findViewById(R.id.spinner);
        mBtnClear = findViewById(R.id.btn_clear);
        mInputFormula2 = findViewById(R.id.edit_input_2);
        mHint1 = findViewById(R.id.hint_1);
        mHint2 = findViewById(R.id.hint_2);
        botonpasos = findViewById(R.id.btn_pasos);
        cardView = findViewById(R.id.card_viewx);
        mBtnClear.setOnClickListener(this);
        mBtnEvaluate.setOnClickListener(this);
        mProgress.hide();
        findViewById(R.id.fab_help).setOnClickListener(this);
        mEditLowerBound = findViewById(R.id.edit_lower);
        mEditUpperBound = findViewById(R.id.edit_upper);
        mLayoutLimit = findViewById(R.id.layout_limit);
        mLayoutLimit.setVisibility(View.GONE);
        mInputFormula.setOnKeyListener(mFormulaOnKeyListener);
        boton = findViewById(R.id.btn_borrar);
        texto = findViewById(R.id.texto);
        texto2 = findViewById(R.id.texto2);
        mResultView = findViewById(R.id.rc_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        mResultView.setHasFixedSize(true);
        mResultView.setLayoutManager(linearLayoutManager);
        mResultAdapter = new ResultAdapter(this);
        mResultView.setAdapter(mResultAdapter);

        texto = findViewById(R.id.texto);
        texto2 = findViewById(R.id.texto2);

        botonpasos.setEnabled(false);


        botonpasos.setOnClickListener(view -> {

             String text = mInputFormula.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text",  text);
            clipboard.setPrimaryClip(clip);


            Toast.makeText(this, "Ecuación copiada al portapapeles", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BaseEvaluatorActivity.this, prueba.class));

        });
        boton.setOnLongClickListener(view -> {
            int length = mInputFormula.getText().length();
            if (length > 0)
               mInputFormula.setText("");


            else if(length==0){

                mInputFormula.setText("");
            }
            return false;
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clickClear();
                break;
            case R.id.btn_solve:
                clickEvaluate();
                Invisiblee();
                break;
            case R.id.fab_help:
                clickHelp();
                break;
        }
    }


    public void Invisiblee(){
        texto.setVisibility(View.GONE);
        texto2.setVisibility(View.GONE);

    }
    public void clickClear() {
        mInputFormula.setText("");
        mEditLowerBound.setText("");
        botonpasos.setEnabled(false);

        mEditUpperBound.setText("");
        mInputFormula2.setText("");
        texto.setVisibility(View.GONE);
        texto2.setVisibility(View.GONE);
    }


    @CallSuper
    public void clickEvaluate() {

        if (mInputFormula.getText().toString().isEmpty()) {
            mInputFormula.requestFocus();
            mInputFormula.setError(getString(R.string.enter_expression));
                botonpasos.setEnabled(false);

            return;
        }

        else{
            botonpasos.setEnabled(true);

        }

        try {
            ExpressionChecker.checkExpression(mInputFormula.getCleanText());
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(mInputFormula, e);
            return;
        }

        String expr = getExpression();
        if (expr == null) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            return;
        }
        Command<ArrayList<String>, String> command = getCommand();

        mProgress.show();
        mBtnEvaluate.setEnabled(false);
        mBtnClear.setEnabled(false);
        hideKeyboard();
        mResultAdapter.clear();

        EvaluateConfig config = EvaluateConfig.loadFromSetting(this);
        CalculateThread calculateThread = new CalculateThread(config, new BaseThread.ResultCallback() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                Log.d(TAG, "onSuccess() called with: result = [" + result + "]");

                hideKeyboard();
                mProgress.hide();
                mBtnEvaluate.setEnabled(true);
                mBtnClear.setEnabled(true);

                  for (String entry : result) {
                    mResultAdapter.addItem(new ResultEntry("", entry));
                }

                if (mResultAdapter.getItemCount() > 0) {
                    mResultView.scrollToPosition(0);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");

                handleExceptions(mInputFormula, e);
                mProgress.hide();
                mBtnEvaluate.setEnabled(true);
                mBtnClear.setEnabled(true);
            }
        });

        calculateThread.execute(command, expr);
    }



    protected void handleExceptions(EditText editText, Exception e) {
        if (e instanceof SyntaxError) {
            int start = Math.min(editText.length(), ((SyntaxError) e).getColumnIndex() - 1);
            int end = Math.min(editText.length(), ((SyntaxError) e).getColumnIndex());
            editText.setSelection(start, end);
            mResultAdapter.clear();
            mResultAdapter.addItem(new ResultEntry("SYNTAX ERROR", e.getMessage()));
        } else if (e instanceof MathException) {
            mResultAdapter.clear();
            mResultAdapter.addItem(new ResultEntry("MATH ERROR", e.getMessage()));
        } else if (e instanceof ParsingException) {
            int start = Math.min(editText.length(), ((ParsingException) e).getIndex());
            int end = Math.min(editText.length(), ((ParsingException) e).getIndex() + 1);
            editText.setSelection(start, end);
            mResultAdapter.clear();
            mResultAdapter.addItem(new ResultEntry("SYNTAX ERROR", e.getMessage()));
        } else {
            mResultAdapter.clear();
            mResultAdapter.addItem(new ResultEntry("Unknown error", e.getMessage()));
        }
        editText.setError("Error!");
    }

    protected String getExpression() {
        return mInputFormula.getCleanText();
    }

    /**
     * show target helper
     */
    public abstract void clickHelp();

    @Override
    public void clickOpenDocument(FunctionDocumentItem functionDocumentItem) {
        MarkdownDocumentActivity.open(this, functionDocumentItem);
    }


    @Nullable
    public abstract Command<ArrayList<String>, String> getCommand();

     public void borrar(View view){
         Invisiblee();
        int length = mInputFormula.getText().length();
        if (length > 0) {
            mInputFormula.getText().delete(length - 1, length);


        }
        else if(length==0){

            mInputFormula.setText("");
        }
    }
    public void asd(View view) {
        Invisiblee();
    mInputFormula.setText(mInputFormula.getText() + ".");
    }
    public void raiz(View view) {
            Invisiblee();
         mInputFormula.setText(mInputFormula.getText() + "Sqrt(");
    }
    public void izquierdo(View view) {
       Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "(");
    }
    public void derecho(View view) {
         Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + ")");
    }

    public void onClickigual(View view) {
    Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "=");
    }
    public void onClick1(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "1");
    }
    public void onClickmas(View view) {
      Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "+");
    }

    public void onClickxd(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "/");
    }
    public void onClickZ(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "z");
    }
    public void onClickmenos(View view) {
    Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "-");
    }
    public void onClickarr(View view) {
      Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "^");
    }
    public void onClickeq(View view) {
       Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "x");
    }
    public void onClickye(View view) {
       Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "y");
    }
    public void onClick2(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "2");
    }
    public void onClick3(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "3");
    }        public void onClick4(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "4");
    }       public void onClick5(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "5");
    }        public void onClick6(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "6");
    }
    public void onClick7(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "7");
    }
    public void onClick8(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "8");
    }
    public void onClick9(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "9");
    }
    public void onClick0(View view) {
        Invisiblee();
        mInputFormula.setText(mInputFormula.getText() + "0");
    }

}
