package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;



import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;

import org.w3c.dom.Text;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab3Estadisticas extends Fragment {

   // private TextView totalMensual;
    // private TextView ltsMensuales;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_estadisticas, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();

        //totalMensual = (TextView) rootView.findViewById(R.id.etTotalMensual);
        //ltsMensuales = (TextView) rootView.findViewById(R.id.etLitrosMensuales);


        //String total = bdHelper.getGastoTotalMesActual(bd);



        //totalMensual.setText("$ 1000000");
        //ltsMensuales.setText("300 lts");

        return rootView;
    }
}


