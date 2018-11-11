package com.example.nicolas.miauto.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicolas.miauto.Clases.Inflado;
import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;

import java.util.Calendar;

public class NuevoInflado extends Fragment {

    public NuevoInflado() { }

    TextView texttemp;
    int presion;
    private ImageButton btnUpdd;
    private ImageButton btnDowndd;
    private ImageButton btnUpdi;
    private ImageButton btnDowndi;
    private ImageButton btnUptd;
    private ImageButton btnDowntd;
    private ImageButton btnUpti;
    private ImageButton btnDownti;
    private ImageButton btnUpau;
    private ImageButton btnDownau;
    private ImageButton btnGuardar;
    private Inflado nuevoInflado;
    private EditText kms;
    private int kmsActual;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.nuevo_inflado,container,false);

        final View myFragmentView = inflater.inflate(R.layout.nuevo_inflado, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();

        kmsActual = bdHelper.dameKilometrajeMaximo(bd);
        kms = (EditText) myFragmentView.findViewById(R.id.etKilometrajeInf);
        kms.setText(String.format(String.valueOf(kmsActual)));

        btnUpdd = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpdd);
        btnUpdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuedd, myFragmentView);
            }
        });
        btnUpdi = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpdi);
        btnUpdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuedi, myFragmentView);
                //goToAttract();
            }
        });
        btnDowndd = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowndd);
        btnDowndd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuedd, myFragmentView);
            }
        });
        btnDowndi = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowndi);
        btnDowndi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuedi, myFragmentView);
                //goToAttract();
            }
        });
        btnUptd = (ImageButton) myFragmentView.findViewById(R.id.btnPressUptd);
        btnUptd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuetd, myFragmentView);
            }
        });
        btnUpti = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpti);
        btnUpti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValueti, myFragmentView);
                //goToAttract();
            }
        });

        btnDowntd = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowntd);
        btnDowntd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuetd, myFragmentView);
            }
        });
        btnDownti = (ImageButton) myFragmentView.findViewById(R.id.btnPressDownti);
        btnDownti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValueti, myFragmentView);
                //goToAttract();
            }
        });

        btnUpau = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpau);
        btnUpau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValueau, myFragmentView);
                //goToAttract();
            }
        });

        btnUpau.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                subirPresion(R.id.tvPressValueau, myFragmentView);
                return true;
            }
        });
        btnDownau = (ImageButton) myFragmentView.findViewById(R.id.btnPressDownau);
        btnDownau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValueau, myFragmentView);
            }
        });
        btnGuardar = (ImageButton) myFragmentView.findViewById(R.id.btnGuardarInflado);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar calendar =  Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                final String[] fecha = new String[1];
                DatePickerDialog dpd = new DatePickerDialog(myFragmentView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        fecha[0] = dayOfMonth + "/" + month + "/" + year;
                        nuevoInflado = dameInflado(myFragmentView, fecha[0]);
                        bdHelper.crearInflado(bd, nuevoInflado);
                        Toast.makeText(getActivity().getApplicationContext(),"Nuevo inflado guardado!",Toast.LENGTH_LONG).show();
                    }
                }, anio, mes, dia);
                dpd.show();
            }
        });
        return myFragmentView;
    }



    public void bajarPresion(int id, View view){
        texttemp = (TextView) view.findViewById(id);
        presion = damePresion(id, view);
        if (presion >0){
            texttemp.setText(String.format(String.valueOf(presion - 1)));
        }
    }
    public void subirPresion(int id, View view){
        texttemp = (TextView) view.findViewById(id);
        presion = damePresion(id, view);
        if (presion < 50){
            texttemp.setText(String.format(String.valueOf(presion + 1)));
        }
    }

    private int damePresion(int id, View view) {
        texttemp = (TextView) view.findViewById(id);
        return Integer.parseInt(texttemp.getText().toString());
    }

    private Inflado dameInflado(View view, String fecha) {
        Inflado in;
        boolean actualizarKM = true;
        int dd = damePresion(R.id.tvPressValuedd, view);
        int di = damePresion(R.id.tvPressValuedi, view);
        int td = damePresion(R.id.tvPressValuetd, view);
        int ti = damePresion(R.id.tvPressValueti, view);
        int au = damePresion(R.id.tvPressValueau, view);
        int kmsAux = Integer.parseInt(kms.getText().toString());
        if (kmsActual == kmsAux) {
            actualizarKM = false;
        }
        in = new Inflado(fecha, dd, di, td, ti, au, kmsAux, actualizarKM);
        return in;
    }
}
