package com.example.nicolas.miauto.Fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nicolas.miauto.Clases.Inflado;
import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.R;

import java.util.List;

import static com.example.nicolas.miauto.BaseDeDatos.bdHelper.getNeumaticos;

public class HistorialInflado extends Fragment {

    public HistorialInflado(){}


    ImageButton btnFecha1;
    ImageButton btnFecha2;
    ImageButton btnFecha3;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private List<Inflado> inflados;
    String idFecha;
    TextView textFecha1;
    TextView textFecha2;
    TextView textFecha3;
    TextView dd;
    TextView di;
    TextView td;
    TextView ti;
    TextView au;
    TextView textAviso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.historial_inflado,container,false);
        final View myFragmentView = inflater.inflate(R.layout.historial_inflado, container, false);
        carsHelper = new baseDatos(getActivity().getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getReadableDatabase();
        inflados = getNeumaticos(bd);
        dd = (TextView) myFragmentView.findViewById(R.id.tvPressValueddH);
        di = (TextView) myFragmentView.findViewById(R.id.tvPressValuediH);
        td = (TextView) myFragmentView.findViewById(R.id.tvPressValuetdH);
        ti = (TextView) myFragmentView.findViewById(R.id.tvPressValuetiH);
        au = (TextView) myFragmentView.findViewById(R.id.tvPressValueauH);
        textFecha3 = (TextView) myFragmentView.findViewById(R.id.tvFecha3);
        textFecha2 = (TextView) myFragmentView.findViewById(R.id.tvFecha2);
        textFecha1 = (TextView) myFragmentView.findViewById(R.id.tvFecha1);
        textAviso = (TextView) myFragmentView.findViewById(R.id.tvAviso);
        btnFecha1 = (ImageButton) myFragmentView.findViewById(R.id.btnFecha1);
        btnFecha2 = (ImageButton) myFragmentView.findViewById(R.id.btnFecha2);
        btnFecha3 = (ImageButton) myFragmentView.findViewById(R.id.btnFecha3);

        setFechas (myFragmentView, inflados);


        btnFecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if(inflados.get(2)!=null){
                    setInfladoVista(myFragmentView,inflados.get(2));
               // }
            }
        });

        btnFecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(inflados.get(1)!=null){
                    setInfladoVista(myFragmentView,inflados.get(1));
               // }
            }
        });

        btnFecha3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if(inflados.get(0)!=null){
                    setInfladoVista(myFragmentView, inflados.get(0));
                //}
            }
        });


return myFragmentView;

    }

    private void setInfladoVista(View myFragmentView, Inflado inflado) {

        dd.setText(String.format(String.valueOf(inflado.getRuedadd())));
        di.setText(String.format(String.valueOf(inflado.getRuedadi())));
        td.setText(String.format(String.valueOf(inflado.getRuedatd())));
        ti.setText(String.format(String.valueOf(inflado.getRuedati())));
        au.setText(String.format(String.valueOf(inflado.getRuedaau())));
    }



    private void setFechas(View view, List<Inflado> inflados ){
        int cant = inflados.size();
        switch (cant){
            case 0 :
                textFecha1.setVisibility(View.INVISIBLE);
                btnFecha1.setVisibility(View.INVISIBLE);
                textFecha2.setVisibility(View.INVISIBLE);
                btnFecha2.setVisibility(View.INVISIBLE);
                textFecha3.setVisibility(View.INVISIBLE);
                btnFecha3.setVisibility(View.INVISIBLE);
                dd.setText("--");
                di.setText("--");
                td.setText("--");
                ti.setText("--");
                au.setText("--");
                textAviso.setVisibility(View.VISIBLE);
                break;
            case 1 :
                //si hay un solo inflado, mostrar solo
                textFecha3.setText(inflados.get(0).getFecha());
                textFecha1.setVisibility(View.INVISIBLE);
                btnFecha1.setVisibility(View.INVISIBLE);
                textFecha2.setVisibility(View.INVISIBLE);
                btnFecha2.setVisibility(View.INVISIBLE);
                setInfladoVista(view, inflados.get(0));
                break;
            case 2:
                textFecha3.setText(inflados.get(0).getFecha());
                textFecha2.setText(inflados.get(1).getFecha());
                textFecha1.setVisibility(View.INVISIBLE);
                btnFecha1.setVisibility(View.INVISIBLE);
                setInfladoVista(view, inflados.get(1));
                break;
            case 3 :
                textFecha3.setText(inflados.get(0).getFecha());
                textFecha2.setText(inflados.get(1).getFecha());
                textFecha1.setText(inflados.get(2).getFecha());
                setInfladoVista(view, inflados.get(2));
                break;
        }
    }

}
