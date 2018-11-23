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

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab3Estadisticas extends Fragment {

    private TextView totalMensual;
    private TextView cargaMaxMensual;
    private TextView mesActual;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private String totalMes;
    private String cargaMaxMes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_estadisticas, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();


        Calendar calendar =  Calendar.getInstance();
        int mes = calendar.get(Calendar.MONTH);

        totalMensual = (TextView) rootView.findViewById(R.id.tvTotalMensualEstadistica);
        cargaMaxMensual = (TextView) rootView.findViewById(R.id.tvCargaMaximaEstadistica);


        mesActual = (TextView) rootView.findViewById(R.id.tvMesActual);

        mesActual.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()).toUpperCase());
        mes = mes+1;

       try {

           //quiero ir a la base de datos y traerme la suma de plata gastada en ese mes
           bd = bdHelper.verificarConexionSL(bd, getActivity().getApplicationContext());
           totalMes = bdHelper.getGastoTotalMesActual(bd, mes);

           if(totalMes != null){
               totalMensual.setText("$ " + totalMes);
           }

           //Quiero ir a la base de datos y traerme la carga maxima en plata
           bd = bdHelper.verificarConexionSL(bd, getActivity().getApplicationContext());
           cargaMaxMes = bdHelper.getCargaMaxMesActual(bd, mes);

           if(totalMes != null){
               cargaMaxMensual.setText("$ " + cargaMaxMes);
           }






       } catch (Exception e){ };








        //totalMensual.setText("$ 1000000");
        //ltsMensuales.setText("300 lts");

        return rootView;
    }
}


