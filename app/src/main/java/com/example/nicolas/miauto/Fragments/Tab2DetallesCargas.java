package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Clases.CargaCombustible;
import com.example.nicolas.miauto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab2DetallesCargas extends Fragment {

    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private ListView lvListaCargas;
    private List<CargaCombustible> listaCargas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_detalle, container, false);

        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();



        lvListaCargas = (ListView) rootView.findViewById(R.id.lvCargas);

        listaCargas = new ArrayList<>();
        listaCargas = bdHelper.getCargas(bd);
        ArrayList listaCargasString = listarCargasString(listaCargas);

        ArrayAdapter adaptador = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listaCargasString);
        lvListaCargas.setAdapter(adaptador);



        return rootView;
    }


    private ArrayList<String> listarCargasString(List<CargaCombustible> listaCargas) {
        ArrayList<String> lista;
        lista = new ArrayList<>();

        for ( CargaCombustible carga:listaCargas
             ) {
            lista.add(carga.toString());
        }
        return lista;
    }


}
