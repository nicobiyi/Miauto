package com.example.nicolas.miauto.Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.nicolas.miauto.Activities.ServiceActivity;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Service> value;

    public CustomAdapter(Context context, List<Service> value) {
        this.context = context;
        this.value=value;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return value.size();
    }
    public void agregarPalabra(String palabra){
        Service s = new Service(palabra,false);
        value.add(s);
    }

    @Override
    public Object getItem(int position) {
        return value.get(position);
    }

    public void tachar(int position, View view, SQLiteDatabase bd){
        TextView simpleCheckedTextView = (TextView) view.findViewById(R.id.checkedTextView);
        simpleCheckedTextView.setText(value.get(position).getNombre());

        if (value.get(position).isTachada()== true) {
            simpleCheckedTextView.setPaintFlags(0);
            value.get(position).setTachada(false);
            bdHelper.normal(bd, value.get(position).getNombre());
        } else {
            simpleCheckedTextView.setPaintFlags(simpleCheckedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            value.get(position).setTachada(true);
            bdHelper.tachar(bd, value.get(position).getNombre());

        }
    }

    public String borrarPalabra(int position, View view){
        String p;
        TextView simpleCheckedTextView = (TextView) view.findViewById(R.id.checkedTextView);
        p = simpleCheckedTextView.getText().toString();
        value.remove(position);
        return p;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.service_lista, null);
        final TextView simpleCheckedTextView = (TextView) view.findViewById(R.id.checkedTextView);
        simpleCheckedTextView.setText(value.get(position).getNombre());
        final int p = position;

        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.get(p).isTachada()== true) {
                    simpleCheckedTextView.setPaintFlags(0);
                    value.get(p).setTachada(false);
                } else {
                    simpleCheckedTextView.setPaintFlags(simpleCheckedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    value.get(p).setTachada(true);
                }
            }
        });

        return view;
    }
}
