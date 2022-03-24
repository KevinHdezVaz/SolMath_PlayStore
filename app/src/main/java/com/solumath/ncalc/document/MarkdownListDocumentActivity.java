package com.solumath.ncalc.document;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.solumath.calculator.R;

import com.solumath.calculator.activities.base.BasseActivity;

public class MarkdownListDocumentActivity extends BasseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment_content);
        setupToolbar();
        setTitle(R.string.documentation);
        MarkdownListDocumentFragment fragment = MarkdownListDocumentFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commitAllowingStateLoss();
    }
}
