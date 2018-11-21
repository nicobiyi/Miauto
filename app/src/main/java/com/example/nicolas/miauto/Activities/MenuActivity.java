package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    TextView tvbtnComb, tvbtnNeum, tvbtnMaps, tvbtnCheck, tvbtnInfo;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    private boolean error = false;
    int kmTemp = -1;

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
        bd = bdHelper.verificarConexionSL(bd, MenuActivity.this);
        setTitle("Miauto - " + bdHelper.damePatente(bd));
        btnEstacionamiento = (ImageView) findViewById(R.id.btnEstacionamiento);
        btnCombustible = (ImageView) findViewById(R.id.btnCombustible);
        btnNeumaticos = (ImageView) findViewById(R.id.btnNeumaticos);
        btnService = (ImageView) findViewById(R.id.btnService);
        btnInfoAuto = (ImageView) findViewById(R.id.btnInfoAuto);
        tvbtnComb = (TextView)findViewById(R.id.tvbtnComb);
        tvbtnNeum = (TextView)findViewById(R.id.tvbtnNeum);
        tvbtnMaps = (TextView)findViewById(R.id.tvbtnMaps);
        tvbtnCheck = (TextView)findViewById(R.id.tvbtnCheck);
        tvbtnInfo = (TextView)findViewById(R.id.tvbtnInfo);

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
                Intent intent = new Intent(MenuActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        btnInfoAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InfoAutoActivity.class);
                startActivity(intent);
            }
        });


        tvbtnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        tvbtnComb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CombustibleActivity.class);
                startActivity(intent);
            }
        });
        tvbtnNeum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NeumaticosActivity.class);
                startActivity(intent);
            }
        });
        tvbtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
        tvbtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InfoAutoActivity.class);
                startActivity(intent);
            }
        });
        popupKm();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bd = bdHelper.verificarConexionSL(bd, MenuActivity.this);
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

    private void popupKm() {
        bd = bdHelper.verificarConexionSL(bd, MenuActivity.this);
        int hayKm = bdHelper.dameKilometrajeMaximo(bd);
        if (hayKm == 0){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Por favor, ingrese su kilometraje actual:");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(viewInflated);

        final EditText edKm = (EditText) viewInflated.findViewById(R.id.dialogEditar);

        edKm.requestFocus();
        if (error==true){
            edKm.setError("¡Error!");
            if (kmTemp >= 0){
                edKm.setText(Integer.toString(kmTemp));
            } else {
                edKm.setHint("Campo obligatorio la primera vez");
            }
            Toast.makeText(getApplicationContext(), "Debe ingresar un kilometraje", Toast.LENGTH_SHORT).show();
        }
        edKm.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String kms = edKm.getText().toString().trim();
                    if (kms.length() == 0) {
                        error = true;
                        Toast.makeText(getApplicationContext(), "Debe ingresar un kilometraje", Toast.LENGTH_SHORT).show();
                        edKm.setHint("Campo obligatorio la primera vez");
                        edKm.setError("¡Error!");
                    } else {
                        error = false;
                        int kilometraje = Integer.parseInt(kms);
                        if (kilometraje<0){
                            Toast.makeText(getApplicationContext(), "El kilometraje no puede ser negativo", Toast.LENGTH_SHORT).show();

                        } else {
                            bd = bdHelper.verificarConexionLE(bd, MenuActivity.this);
                            bdHelper.cargarKm(bd, Fechador.dameFechaActual(), kilometraje);
                            Toast.makeText(getApplicationContext(), "Kilometraje ingresado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    }
                }

                return false;
            }
        });

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String kms = edKm.getText().toString().trim();
                if (kms.length() == 0) {
                    error = true;
                    popupKm();
                } else {
                    error = false;
                    int kilometraje = Integer.parseInt(kms);
                    if (kilometraje<0){
                        Toast.makeText(getApplicationContext(), "El kilometraje no puede ser negativo", Toast.LENGTH_SHORT).show();
                        error = true;
                        kmTemp=-1;
                        popupKm();
                    } else {
                        bd = bdHelper.verificarConexionLE(bd, MenuActivity.this);
                        bdHelper.cargarKm(bd, Fechador.dameFechaActual(), kilometraje);
                        Toast.makeText(getApplicationContext(), "Kilometraje ingresado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

       builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
           @Override
           public void onCancel(DialogInterface dialog) {
               error = true;
               String kms = edKm.getText().toString().trim();
               if (kms.length() != 0) {
                   kmTemp=Integer.parseInt(edKm.getText().toString());
               }
               popupKm();
                         }
       });
        AlertDialog dialog = builder.create();
        dialog.show();

        }
    }
}
