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

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.vazk.calculator.symja.tokenizer.ExpressionTokenizer;

/**
Creado por mi jaja
 */
public abstract class AbstractooCalculatorActivity extends NavDrawerActivity implements ICalculator {
    public ExpressionTokenizer mTokenizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokenizer = new ExpressionTokenizer();
    }


    public abstract void insertText(String s);

    public abstract void insertOperator(String s);



    public abstract void setTextDisplay(String text);


}
