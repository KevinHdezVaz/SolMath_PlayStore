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

package com.solumath.calculator.activities.base;

import android.content.Intent;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.solumath.calculator.R;

import com.solumath.calculator.symja.activities.DerivativeActivity;
import com.solumath.calculator.symja.activities.ExpandAllExpressionActivity;
import com.solumath.calculator.symja.activities.FactorExpressionActivity;
import com.solumath.calculator.symja.activities.FactorPrimeActivity;
import com.solumath.calculator.symja.activities.IdeActivity;
import com.solumath.calculator.symja.activities.IntegrateActivity;
import com.solumath.calculator.symja.activities.LimitActivity;
import com.solumath.calculator.symja.activities.ModuleActivity;
import com.solumath.calculator.symja.activities.NumberActivity;
import com.solumath.calculator.symja.activities.PermutationActivity;
import com.solumath.calculator.symja.activities.PiActivity;
import com.solumath.calculator.symja.activities.PrimitiveActivity;
import com.solumath.calculator.symja.activities.SimplifyExpressionActivity;
import com.solumath.calculator.symja.activities.SolveEquationActivity;
import com.solumath.calculator.symja.activities.TrigActivity;
import com.solumath.ncalc.calculator.BasicCalculatorActivity;
import com.solumath.ncalc.calculator.LogicCalculatorActivity;
import com.solumath.ncalc.document.MarkdownListDocumentActivity;
import com.solumath.ncalc.document.info.InfoActivity;
import com.solumath.ncalc.geom2d.GeometryDescartesActivity;
import com.solumath.ncalc.graph.GraphActivity;
import com.solumath.ncalc.matrix.MatrixCalculatorActivity;
import com.solumath.ncalc.settings.SettingsActivity;
import com.solumath.ncalc.systemequations.SystemEquationActivity;
import com.solumath.ncalc.unitconverter.UnitCategoryActivity;

import static com.solumath.calculator.symja.models.TrigItem.TRIG_TYPE.EXPAND;
import static com.solumath.calculator.symja.models.TrigItem.TRIG_TYPE.EXPONENT;
import static com.solumath.calculator.symja.models.TrigItem.TRIG_TYPE.REDUCE;

/**
 * Created by Duy on 19/7/2016
 */
public abstract class NavDrawerActivity extends BasseActivity {
    protected final Handler handler = new Handler();






    @Override
    protected void onResume() {
        super.onResume();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //PARA QUE APAREZCA LA FLECHA DE ATRAS
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });


    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.action_all_functions: {
                intent = new Intent(getApplicationContext(), MarkdownListDocumentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.action_ide_mode:
                intent = new Intent(getApplicationContext(), IdeActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_sci_calc:
                intent = new Intent(getApplicationContext(), BasicCalculatorActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_graph:
                intent = new Intent(getApplicationContext(), GraphActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_unit:
                intent = new Intent(getApplicationContext(), UnitCategoryActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_base:
                intent = new Intent(getApplicationContext(), LogicCalculatorActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_geometric_descartes:
                intent = new Intent(getApplicationContext(), GeometryDescartesActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_setting:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_about_app:
                intent = new Intent(getApplicationContext(), InfoActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_matrix:
                intent = new Intent(getApplicationContext(), MatrixCalculatorActivity.class);
                postStartActivity(intent);
                break;
            case R.id.system_equation:
                intent = new Intent(getApplicationContext(), SystemEquationActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_solve_equation:
                intent = new Intent(getApplicationContext(), SolveEquationActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_simplify_equation:
                intent = new Intent(getApplicationContext(), SimplifyExpressionActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_factor_equation:
                intent = new Intent(getApplicationContext(), FactorExpressionActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_derivitive:
                intent = new Intent(getApplicationContext(), DerivativeActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_expand_binomial:
                intent = new Intent(getApplicationContext(), ExpandAllExpressionActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_limit:
                intent = new Intent(getApplicationContext(), LimitActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_integrate:
                intent = new Intent(getApplicationContext(), IntegrateActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_primitive:
                intent = new Intent(getApplicationContext(), PrimitiveActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_rate:
                gotoPlayStore();
                break;
            case R.id.nav_prime_factor:
                intent = new Intent(getApplicationContext(), FactorPrimeActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_mod:
                intent = new Intent(getApplicationContext(), ModuleActivity.class);
                postStartActivity(intent);
                break;
            case R.id.nav_trig_expand:
                intent = new Intent(getApplicationContext(), TrigActivity.class);
                intent.putExtra(TrigActivity.TYPE, EXPAND);
                postStartActivity(intent);
                break;
            case R.id.nav_trig_reduce:
                intent = new Intent(getApplicationContext(), TrigActivity.class);
                intent.putExtra(TrigActivity.TYPE, REDUCE);
                postStartActivity(intent);
                break;
            case R.id.nav_trig_to_exp:
                intent = new Intent(getApplicationContext(), TrigActivity.class);
                intent.putExtra(TrigActivity.TYPE, EXPONENT);
                postStartActivity(intent);
                break;
            case R.id.nav_permutation:
                intent = new Intent(getApplicationContext(), PermutationActivity.class);
                if (this instanceof PermutationActivity) {
                    finish();
                }
                intent.putExtra(PermutationActivity.TYPE_NUMBER, PermutationActivity.TYPE_PERMUTATION);
                postStartActivity(intent);
                break;
            case R.id.nav_combination:
                intent = new Intent(getApplicationContext(), PermutationActivity.class);
                if (this instanceof PermutationActivity) {
                    finish();
                }
                intent.putExtra(PermutationActivity.TYPE_NUMBER, PermutationActivity.TYPE_COMBINATION);
                postStartActivity(intent);
                break;
            case R.id.nav_catalan:
                intent = new Intent(getApplicationContext(), NumberActivity.class);
                if (this instanceof NumberActivity) {
                    finish();
                }
                intent.putExtra(NumberActivity.DATA, NumberActivity.NumberType.CATALAN);
                postStartActivity(intent);
                break;
            case R.id.nav_fibo:
                intent = new Intent(getApplicationContext(), NumberActivity.class);
                if (this instanceof NumberActivity) {
                    finish();
                }
                intent.putExtra(NumberActivity.DATA, NumberActivity.NumberType.FIBONACCI);
                postStartActivity(intent);
                break;
            case R.id.nav_prime:
                intent = new Intent(getApplicationContext(), NumberActivity.class);
                if (this instanceof NumberActivity) {
                    finish();
                }
                intent.putExtra(NumberActivity.DATA, NumberActivity.NumberType.PRIME);
                postStartActivity(intent);
                break;
            case R.id.action_divisors:
                intent = new Intent(getApplicationContext(), NumberActivity.class);
                if (this instanceof NumberActivity) {
                    finish();
                }
                intent.putExtra(NumberActivity.DATA, NumberActivity.NumberType.DIVISORS);
                postStartActivity(intent);
                break;
            case R.id.action_pi_number:
                intent = new Intent(getApplicationContext(), PiActivity.class);
                postStartActivity(intent);
                break;
        }
        return true;
    }

    private void postStartActivity(final Intent intent) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 100);
    }

}
