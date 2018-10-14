package com.example.nicolas.miauto.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.miauto.Clases.CargaCombustible;
import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Genericos.Fechador;
import com.example.nicolas.miauto.R;

import java.util.Calendar;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class Tab1Carga extends Fragment {

    private EditText etFecha;
    private EditText etLitros;
    private EditText etTotal;
    private EditText etKm;
    private Button btn;
    private int kmMax;
    private CargaCombustible nuevaCarga;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private String fechaActual;

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

        etFecha = (EditText) myFragmentView.findViewById(R.id.edFechaCargaCombustible);
        etLitros = (EditText) myFragmentView.findViewById(R.id.etLitros);
        etTotal = (EditText) myFragmentView.findViewById(R.id.etTotalCarga);
        etKm = (EditText) myFragmentView.findViewById(R.id.etKilometraje);
        btn = (Button) myFragmentView.findViewById(R.id.btnGuardarCarga);

        //busco y autocompleto el Km mayor que tenga la bd
        kmMax = bdHelper.dameKilometrajeMaximo(bd);
        etKm.setText(Integer.toString(kmMax));

        //autocompleto con la fecha de hoy la fecha
        fechaActual = Fechador.dameFechaActual();
        etFecha.setText(fechaActual);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar =  Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(myFragmentView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        etFecha.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, anio, mes, dia);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etFecha.setText(fechaActual);
                    }
                });
                dpd.show();
            }
        });

        //accion del boton
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarInputs()) {

                    //Guardar en Base de Datos
                    nuevaCarga = capturarCargaCombustible();

                    try {
                        bdHelper.guardarCargaCombustible(bd, nuevaCarga);
                        // Avisar al usuario que se guardo OK
                        Toast.makeText(getActivity().getApplicationContext(),"Carga de combustible realizada con éxito", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Error al cargar combustible", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Por favor, completar los campos obligatorios", Toast.LENGTH_LONG).show();

                }


            }

        });

        return myFragmentView;
    }

    private void dameFecha() {
        Calendar calendar =  Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        mes++;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        }, anio, mes, dia);
        dpd.show();
    }


    private CargaCombustible capturarCargaCombustible (){
        CargaCombustible carga;
        boolean actualizarKM = true;
                String fecha = etFecha.getText().toString();
                double litros = Double.parseDouble(etLitros.getText().toString());
                double total = Double.parseDouble(etTotal.getText().toString());
                int km = Integer.parseInt(etKm.getText().toString());
                if (km == kmMax) {
                    actualizarKM = false;
                }
                carga = new CargaCombustible(fecha, km, litros, total, actualizarKM);

        return carga;
    }

    public boolean validarInputs(){
        boolean ok = true;

        if(TextUtils.isEmpty(etFecha.getText().toString())) {
            etFecha.setError("Este campo es obligatorio.");
            ok = false;
        }

        if(TextUtils.isEmpty(etLitros.getText().toString())) {
            etLitros.setError("Este campo es obligatorio.");
            ok = false;
        }

        if(TextUtils.isEmpty(etTotal.getText().toString())) {
            etTotal.setError("Este campo es obligatorio.");
            ok = false;
        }

        if(TextUtils.isEmpty(etKm.getText().toString())) {
            etKm.setError("Este campo es obligatorio.");
            ok = false;
        }

        return ok;
    }


}
