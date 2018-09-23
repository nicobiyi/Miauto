package com.example.nicolas.miauto.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nicolas.miauto.R;

public class GarageActivity extends Activity {

    Button btnAuto;
    ImageButton btnBorrarAuto;
    boolean hayAuto;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getWritableDatabase();
        hayAuto = bdHelper.hayAuto(bd);
        btnAuto = (Button) findViewById(R.id.btnSelectAuto);
        btnBorrarAuto = (ImageButton) findViewById(R.id.btnBorrarAuto);
        if (hayAuto){
            btnBorrarAuto.setVisibility(View.VISIBLE);
            btnAuto.setText(bdHelper.damePatente(bd));
            btnAuto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GarageActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            btnAuto.setText("Crear Auto...");
            btnAuto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GarageActivity.this, FormAutoActivity.class);
                    startActivity(intent);
                }
            });
        }

        btnBorrarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdHelper.eliminarAuto(bd);
                btnBorrarAuto.setVisibility(View.INVISIBLE);
                btnAuto.setText("Crear Auto...");
                Toast.makeText(getApplicationContext(),"Auto eliminado correctamente!",Toast.LENGTH_LONG).show();
                btnAuto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GarageActivity.this, FormAutoActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        //chequea permisos GPS
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }


}
