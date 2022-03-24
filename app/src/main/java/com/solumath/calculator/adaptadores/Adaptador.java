package com.solumath.calculator.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.solumath.calculator.R;

import java.util.ArrayList;



public class Adaptador extends BaseAdapter {

    private Context context;
    private ArrayList< lista> listItem;


    public Adaptador(Context context, ArrayList< lista> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

         lista Item =(lista)getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.modelo,null);
        ImageView imagen = (ImageView)view.findViewById(R.id.imagen3);
        TextView titulo = (TextView)view.findViewById(R.id.titulox);

        imagen.setImageResource(Item.getImgFoto());
        titulo.setText(Item.getTitulo());
        return view;

    }
}