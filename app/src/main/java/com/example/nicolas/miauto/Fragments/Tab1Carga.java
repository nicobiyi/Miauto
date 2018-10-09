package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.miauto.Activities.CargaCombustible;
import com.example.nicolas.miauto.Activities.baseDatos;
import com.example.nicolas.miauto.Activities.bdHelper;
import com.example.nicolas.miauto.R;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab1Carga extends Fragment {

    private EditText etFecha;
    private EditText etLitros;
    private EditText etTotal;
    private EditText etKm;
    private Button btn;

    private CargaCombustible nuevaCarga;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_carga, container, false);

        /************************************************
         CODIGO PROPIO PARA CAPTURAR EL INPUT DEL USUARIO
         ************************************************/
        final View myFragmentView = inflater.inflate(R.layout.tab1_carga, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();

        etFecha = (EditText) myFragmentView.findViewById(R.id.etFechaCarga);
        etLitros = (EditText) myFragmentView.findViewById(R.id.etLitros);
        etTotal = (EditText) myFragmentView.findViewById(R.id.etTotalCarga);
        etKm = (EditText) myFragmentView.findViewById(R.id.etKilometraje);
        btn = (Button) myFragmentView.findViewById(R.id.btnGuardarCarga);

        //busco y autocompleto el Km mayor que tenga la bd

        int kmMax = bdHelper.dameKilometrajeMaximo(bd);
        etKm.setText(Integer.toString(kmMax));
        //Toast.makeText(getActivity().getApplicationContext(), "datos:" + bdHelper.dameUltimaCarga(bd), Toast.LENGTH_LONG).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar en Base de Datos
                nuevaCarga = capturarCargaCombustible(myFragmentView);
                try {
                    bdHelper.guardarCargaCombustible(bd, nuevaCarga);
                    // Avisar al usuario que se guardo OK
                    Toast.makeText(getActivity().getApplicationContext(),"TEXTO PRUEBA", Toast.LENGTH_LONG).show();
                    // vaciar los EditText para una nueva carga
                    etFecha.setText("");
                    etLitros.setText("");
                    etTotal.setText("");
                    etKm.setText(bdHelper.dameKilometrajeMaximo(bd));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            }

        });

        return myFragmentView;
    }


    private CargaCombustible capturarCargaCombustible (View view){
        CargaCombustible carga;
        String fecha = "27/09/2018";
        double litros = Double.parseDouble(etLitros.getText().toString());
        double total = Double.parseDouble(etTotal.getText().toString());
        int km = Integer.parseInt(etKm.getText().toString());

        carga = new CargaCombustible(fecha, km, litros, total);
        return carga;
    }
}
