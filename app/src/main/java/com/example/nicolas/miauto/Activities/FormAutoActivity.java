package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Clases.Auto;
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
        etPatente = (EditText)findViewById(R.id.etPatente2);
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
                if (chequearTextosVacios()){
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
        //mostrar teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        if (bdHelper.hayAuto(bd)){
            Intent intent = new Intent(FormAutoActivity.this, GarageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!chequearTextosLLenos()) {
            Intent intent = new Intent(FormAutoActivity.this, GarageActivity.class);
            startActivity(intent);
        } else {
            alertaDiscardChanges();
        }
        return true;
    }

    private void alertaDiscardChanges() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormAutoActivity.this);
        builder.setMessage("Los datos ingresados se perderán")
                .setTitle("Descartar cambios?");

        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(FormAutoActivity.this, GarageActivity.class);
                startActivity(intent);
            }
        });
        builder.setIcon(R.drawable.remove_car);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no hace nada
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();    }

    private boolean chequearTextosLLenos() {
        String texto;
        texto = etPatente.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        texto = etMarca.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        texto = etModelo.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        texto = etChasis.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        texto = etMotor.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        texto = etTipo.getText().toString();
        if(!TextUtils.isEmpty(texto)) {
            return true;
        }
        return false;
    }

    private boolean chequearTextosVacios() {
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
        //texto = etChasis.getText().toString();
        //if(TextUtils.isEmpty(texto)) {
            //etChasis.setError("Este campo es obligatorio.");
            //ok= false;
        //}
        //texto = etMotor.getText().toString();
        //if(TextUtils.isEmpty(texto)) {
            //etMotor.setError("Este campo es obligatorio.");
            //ok= false;
        //}
        texto = etTipo.getText().toString();
        if(TextUtils.isEmpty(texto)) {
            etTipo.setError("Este campo es obligatorio.");
            ok= false;
        }
        return ok;
    }
}
