package com.vazk.calculator.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.vazk.calculator.R;
import com.vazk.calculator.adaptadores.CustomAdapter;
import com.vazk.calculator.adaptadores.GridView_adapter;
import com.vazk.calculator.symja.activities.ExpandAllExpressionActivity;
import com.vazk.calculator.symja.activities.FactorExpressionActivity;
import com.vazk.calculator.symja.activities.SimplifyExpressionActivity;
import com.vazk.calculator.symja.activities.SolveEquationActivity;
import com.vazk.ncalc.calculator.BasicCalculatorActivity;
import com.vazk.ncalc.calculator.LogicCalculatorActivity;
import com.vazk.ncalc.geom2d.GeometryDescartesActivity;
import com.vazk.ncalc.graph.GraphActivity;
import com.vazk.ncalc.matrix.MatrixCalculatorActivity;
import com.vazk.ncalc.systemequations.SystemEquationActivity;
import com.vazk.ncalc.unitconverter.UnitCategoryActivity;

import java.util.ArrayList;

public class menuCalculo extends AppCompatActivity {

    private CustomAdapter adaptador;
    private GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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



        grid = (GridView)  findViewById(R.id.item);

        final ArrayList<GridView_adapter> listItems = new ArrayList<>();
        listItems.add(new GridView_adapter(R.drawable.calculadora,getString(R.string.science_calculator)));
        listItems.add(new GridView_adapter((R.drawable.calculadorr), getString(R.string.logic_calculator)));
        listItems.add(new GridView_adapter((R.drawable.ecuacion), getString(R.string.solve_equation)));
        listItems.add(new GridView_adapter((R.drawable.matriz), getString(R.string.matrix)));





        adaptador = new CustomAdapter(this, listItems);
        grid.setAdapter(adaptador);

        grid.setOnItemClickListener((adapterView, view, i, l) -> {

            if(i == 0 ){
                startActivity(new Intent(menuCalculo.this, BasicCalculatorActivity.class));
            }
            if(i == 1 ){
                startActivity(new Intent(menuCalculo.this, LogicCalculatorActivity.class));
            }
            if(i == 2 ){
                startActivity(new Intent(menuCalculo.this, SolveEquationActivity.class));

            }
            if(i == 3 ){
                startActivity(new Intent(menuCalculo.this, MatrixCalculatorActivity.class));
            }

        });


    }
}
