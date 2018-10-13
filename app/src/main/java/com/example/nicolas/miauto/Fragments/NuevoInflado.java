package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicolas.miauto.Clases.Inflado;
import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;

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
    private String fecha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.nuevo_inflado,container,false);

        final View myFragmentView = inflater.inflate(R.layout.nuevo_inflado, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();

        kmsActual = bdHelper.dameKilometrajeMaximo(bd);
        kms = (EditText) myFragmentView.findViewById(R.id.etKilometrajeInf);
        kms.setText(String.format(String.valueOf(kmsActual)));

        ImageButton btnUpdd = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpdd);
        btnUpdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuedd, myFragmentView);
            }
        });
        ImageButton btnUpdi = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpdi);
        btnUpdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuedi, myFragmentView);
                //goToAttract();
            }
        });
        ImageButton btnDowndd = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowndd);
        btnDowndd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuedd, myFragmentView);
            }
        });
        ImageButton btnDowndi = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowndi);
        btnDowndi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuedi, myFragmentView);
                //goToAttract();
            }
        });
        ImageButton btnUptd = (ImageButton) myFragmentView.findViewById(R.id.btnPressUptd);
        btnUptd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValuetd, myFragmentView);
            }
        });
        ImageButton btnUpti = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpti);
        btnUpti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValueti, myFragmentView);
                //goToAttract();
            }
        });

        ImageButton btnDowntd = (ImageButton) myFragmentView.findViewById(R.id.btnPressDowntd);
        btnDowntd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValuetd, myFragmentView);
            }
        });
        ImageButton btnDownti = (ImageButton) myFragmentView.findViewById(R.id.btnPressDownti);
        btnDownti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValueti, myFragmentView);
                //goToAttract();
            }
        });

        ImageButton btnUpau = (ImageButton) myFragmentView.findViewById(R.id.btnPressUpau);
        btnUpau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirPresion(R.id.tvPressValueau, myFragmentView);
                //goToAttract();
            }
        });
        ImageButton btnDownau = (ImageButton) myFragmentView.findViewById(R.id.btnPressDownau);
        btnDownau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajarPresion(R.id.tvPressValueau, myFragmentView);
            }
        });
        ImageButton btnGuardar = (ImageButton) myFragmentView.findViewById(R.id.btnGuardarInflado);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nuevoInflado = dameInflado(myFragmentView);
                bdHelper.crearInflado(bd, nuevoInflado);
                Toast.makeText(getActivity().getApplicationContext(),"Nuevo inflado guardado!",Toast.LENGTH_LONG).show();

            }
        });
        return myFragmentView;
    }



    public void bajarPresion(int id, View view){
        texttemp = (TextView) view.findViewById(id);
        presion = damePresion(id, view);
        texttemp.setText(String.format(String.valueOf(presion - 1)));
    }
    public void subirPresion(int id, View view){
        texttemp = (TextView) view.findViewById(id);
        presion = damePresion(id, view);
        texttemp.setText(String.format(String.valueOf(presion + 1)));
    }

    private int damePresion(int id, View view) {
        texttemp = (TextView) view.findViewById(id);
        return Integer.parseInt(texttemp.getText().toString());
    }

    /*private Inflado dameInflado (View view){
        Inflado in;

        int dd =damePresion(R.id.tvPressValuedd, view);
        int di =damePresion(R.id.tvPressValuedi, view);
        int td =damePresion(R.id.tvPressValuetd, view);
        int ti =damePresion(R.id.tvPressValueti, view);
        int au =damePresion(R.id.tvPressValueau, view);
        in = new Inflado("27/09/2018", dd, di, td, ti, au);
        return in;
    }*/

    private Inflado dameInflado (View view) {
        Inflado in;
        boolean actualizarKM = true;
        int dd = damePresion(R.id.tvPressValuedd, view);
        int di = damePresion(R.id.tvPressValuedi, view);
        int td = damePresion(R.id.tvPressValuetd, view);
        int ti = damePresion(R.id.tvPressValueti, view);
        int au = damePresion(R.id.tvPressValueau, view);
        int kmsAux = Integer.parseInt(kms.getText().toString());
        //String fecha = Fechador.dameFecha();
        String fecha = "09/10/2018";
        if (kmsActual == kmsAux) {
            actualizarKM = false;
        }
        in = new Inflado(fecha, dd, di, td, ti, au, kmsAux, actualizarKM);
        return in;
    }
}
