package com.solumath.calculator;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.solumath.ncalc.calculator.BasicCalculatorActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class BaseTestCase {
    @Rule
    public ActivityTestRule<BasicCalculatorActivity> rule
            = new ActivityTestRule<>(BasicCalculatorActivity.class);
}
