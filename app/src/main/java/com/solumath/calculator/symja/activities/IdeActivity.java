package com.solumath.calculator.symja.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.solumath.calculator.R;
import com.solumath.calculator.activities.base.BasseEvaluatorActivity;
import com.solumath.calculator.evaluator.EvaluateConfig;
import com.solumath.calculator.evaluator.MathEvaluator;
import com.solumath.calculator.evaluator.thread.Command;
import com.solumath.ncalc.document.MarkdownListDocumentActivity;
import com.gx.common.collect.Lists;

import java.util.ArrayList;

public class IdeActivity extends BasseEvaluatorActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBtnEvaluate.setText(R.string.eval);
    }

    @Override
    public void clickHelp() {
        Intent intent = new Intent(this, MarkdownListDocumentActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @WorkerThread
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }
}
