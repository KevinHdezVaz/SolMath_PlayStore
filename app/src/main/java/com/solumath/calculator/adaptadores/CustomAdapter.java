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




public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GridView_adapter> listItem;


    public CustomAdapter(Context context, ArrayList<GridView_adapter> listItem) {
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

        GridView_adapter Item =(GridView_adapter)getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.model3,null);
        ImageView imagen = (ImageView)view.findViewById(R.id.imagen3);
        TextView titulo = (TextView)view.findViewById(R.id.titulox);

        imagen.setImageResource(Item.getImgFoto());
        titulo.setText(Item.getTitulo());
        return view;

    }
}