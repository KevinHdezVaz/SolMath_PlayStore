package com.vazk.calculator.symja.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.vazk.calculator.R;
import com.vazk.calculator.activities.base.BaseEvaluatorActivity;
import com.vazk.calculator.evaluator.EvaluateConfig;
import com.vazk.calculator.evaluator.MathEvaluator;
import com.vazk.calculator.evaluator.thread.Command;
import com.vazk.ncalc.document.MarkdownListDocumentActivity;
import com.gx.common.collect.Lists;

import java.util.ArrayList;

public class IdeActivity extends BaseEvaluatorActivity {
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
