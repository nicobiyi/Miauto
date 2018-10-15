package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Genericos.Fechador;
import com.example.nicolas.miauto.R;

public class MenuActivity extends Activity {
    ImageView btnEstacionamiento;
    ImageView btnCombustible;
    ImageView btnNeumaticos;
    ImageView btnInfoAuto;
    ImageView btnService;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getReadableDatabase();
        if (!bdHelper.hayAuto(bd)){
            Intent intent = new Intent(MenuActivity.this, GarageActivity.class);
            startActivity(intent);
        }
        setTitle("Miauto - " + bdHelper.damePatente(bd));
        btnEstacionamiento = (ImageView) findViewById(R.id.btnEstacionamiento);
        btnCombustible = (ImageView) findViewById(R.id.btnCombustible);
        btnNeumaticos = (ImageView) findViewById(R.id.btnNeumaticos);
        btnService = (ImageView) findViewById(R.id.btnService);
        btnInfoAuto = (ImageView) findViewById(R.id.btnInfoAuto);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        btnEstacionamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        btnCombustible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CombustibleActivity.class);
                startActivity(intent);
            }
        });
        btnNeumaticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NeumaticosActivity.class);
                startActivity(intent);
            }
        });
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnInfoAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actualizarKm();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bdHelper.hayAuto(bd)){
            Intent intent = new Intent(MenuActivity.this, GarageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MenuActivity.this, GarageActivity.class);
        startActivity(intent);
        return true;
    }
    private void actualizarKm() {
        int hayKm = bdHelper.dameKilometrajeMaximo(bd);
        if (hayKm == 0){
            popupKm();
        }
    }

    private void popupKm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Por favor, ingrese su kilometraje actual:");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(viewInflated);

        final EditText edKm = (EditText) viewInflated.findViewById(R.id.editar);

        edKm.requestFocus();
        if (error==true){
            edKm.setError("¡Error!");
            edKm.setHint("Campo obligatorio la primera vez");
            Toast.makeText(getApplicationContext(), "Debe ingresar un kilometraje", Toast.LENGTH_SHORT).show();
        }

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String kms = edKm.getText().toString().trim();
                if (kms.length() == 0) {
                    error = true;
                    popupKm();
                } else {
                    int kilometraje = Integer.parseInt(kms);
                    bdHelper.cargarKm(bd, Fechador.dameFechaActual(), kilometraje);
                    Toast.makeText(getApplicationContext(), "Kilometraje ingresado correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

       builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
           @Override
           public void onCancel(DialogInterface dialog) {
               error = true;
               popupKm();
                         }
       });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
