package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.miauto.R;

public class FormAutoActivity extends Activity {
    EditText etPatente;
    EditText etMarca;
    EditText etModelo;
    EditText etChasis;
    EditText etMotor;
    EditText etTipo;
    Button btnRegistrarAuto;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_auto);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        etPatente = (EditText)findViewById(R.id.etPatente);
        etPatente.requestFocus();
        etMarca = (EditText)findViewById(R.id.etMarca);
        etModelo = (EditText)findViewById(R.id.etModelo);
        etChasis = (EditText)findViewById(R.id.etChasis);
        etMotor = (EditText)findViewById(R.id.etMotor);
        etTipo = (EditText)findViewById(R.id.etTipo);
        btnRegistrarAuto = (Button)findViewById(R.id.btnRegistrarAuto);
        btnRegistrarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chequearTextos()){
                    String patente= etPatente.getText().toString();
                    String marca= etMarca.getText().toString();
                    String modelo= etModelo.getText().toString();
                    String chasis= etChasis.getText().toString();
                    String motor= etMotor.getText().toString();
                    String tipo= etTipo.getText().toString();
                    Auto auto = new Auto(patente, marca, modelo, tipo, chasis, motor);
                    bdHelper.crearAuto(bd, auto);
                    Toast.makeText(getApplicationContext(),"Auto creado correctamente!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FormAutoActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(FormAutoActivity.this, GarageActivity.class);
        startActivity(intent);
        return true;
    }

    private boolean chequearTextos() {
        boolean ok = true;
        String texto;
        texto = etPatente.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etPatente.setError("Este campo es obligatorio.");
            ok= false;
        }
        texto = etMarca.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etMarca.setError("Este campo es obligatorio.");
            ok= false;
        }
        texto = etModelo.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etModelo.setError("Este campo es obligatorio.");
            ok= false;
        }
        texto = etChasis.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etChasis.setError("Este campo es obligatorio.");
            ok= false;
        }
        texto = etMotor.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etMotor.setError("Este campo es obligatorio.");
            ok= false;
        }
        texto = etTipo.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etTipo.setError("Este campo es obligatorio.");
            ok= false;
        }
        return ok;
    }
}
