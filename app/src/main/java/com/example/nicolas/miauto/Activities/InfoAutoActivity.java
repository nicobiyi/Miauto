package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Clases.Auto;
import com.example.nicolas.miauto.R;

public class InfoAutoActivity extends Activity {
    private static final String CAMPOOBLIGATORIO = "Este campo es obligatorio.";
    private EditText etPatente;
    private EditText etMarca;
    private EditText etModelo;
    private EditText etChasis;
    private EditText etMotor;
    private EditText etTipo;
    private Button btnRegistrarAuto;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_auto);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        etMarca = (EditText)findViewById(R.id.etMarca2);
        etModelo = (EditText)findViewById(R.id.etModelo2);
        etChasis = (EditText)findViewById(R.id.etChasis2);
        etMotor = (EditText)findViewById(R.id.etMotor2);
        etPatente = (EditText)findViewById(R.id.etPatente2);
        etTipo = (EditText)findViewById(R.id.etTipo2);
        btnRegistrarAuto = (Button)findViewById(R.id.btngActualizarDatos);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();
        cargarDatos();
        etPatente.requestFocus();
        btnRegistrarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chequearTextosVacios()){
                    String patente= etPatente.getText().toString().toUpperCase();
                    String marca= etMarca.getText().toString();
                    String modelo= etModelo.getText().toString();
                    String chasis= etChasis.getText().toString();
                    String motor= etMotor.getText().toString();
                    String tipo= etTipo.getText().toString();
                    Auto auto = new Auto(patente, marca, modelo, tipo, chasis, motor);
                    bd = bdHelper.verificarConexionLE(bd, InfoAutoActivity.this);
                    bdHelper.eliminarDatosAuto(bd);
                    bd = bdHelper.verificarConexionLE(bd, InfoAutoActivity.this);
                    bdHelper.crearAuto(bd, auto);
                    Toast.makeText(getApplicationContext(),"Auto modificado exitosamente!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(InfoAutoActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean chequearTextosVacios() {
        boolean ok = true;
        String texto;
        texto = etPatente.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etPatente.setError(CAMPOOBLIGATORIO);
            ok= false;
        } else if (!validarPatente(texto)){
            ok= false;
        }
        texto = etMarca.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etMarca.setError(CAMPOOBLIGATORIO);
            ok= false;
        }
        texto = etModelo.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etModelo.setError(CAMPOOBLIGATORIO);
            ok= false;
        }
        texto = etTipo.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etTipo.setError(CAMPOOBLIGATORIO);
            ok= false;
        }
        return ok;
    }

    private void cargarDatos() {
        bd = bdHelper.verificarConexionSL(bd, InfoAutoActivity.this);
        Auto auto = bdHelper.dameAuto(bd);
        if (auto!=null){
            etMarca.setText(auto.getMarca());
            etModelo.setText(auto.getModelo());
            etChasis.setText(auto.getChasis());
            etPatente.setText(auto.getPatente());
            etTipo.setText(auto.getTipo());
            etMotor.setText(auto.getMotor());
        } else {
            Toast.makeText(getApplicationContext(), "No hay un auto cargado. ERROR!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(InfoAutoActivity.this, MenuActivity.class);
        startActivity(intent);
        return true;
    }

    public boolean validarPatente(String patente){
        boolean ok = false;
        if (patente.length() == 6 || patente.length()== 7 ){
            ok = true;
        } else {
            etPatente.setError("La patente tiene que tener 6 o 7 caracteres.");
        }
        return ok;
    }
}
