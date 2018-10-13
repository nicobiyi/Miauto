package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab2DetallesCargas extends Fragment {

    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_detalle, container, false);



        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();

        btn = (Button) rootView.findViewById(R.id.btnTEST);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double ultimaCarga = bdHelper.dameUltimaCarga(bd);
                // Avisar al usuario que se guardo OK
                Toast.makeText(getActivity().getApplicationContext(),"total de carga $" + ultimaCarga, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }




}
