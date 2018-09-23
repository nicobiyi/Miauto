package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.nicolas.miauto.R;

public class MenuActivity extends Activity {
    Button btnEstacionamiento;
    Button btnCombustible;
    Button btnNeumaticos;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;

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
        btnEstacionamiento = (Button) findViewById(R.id.btnEstacionamiento);
        btnCombustible = (Button) findViewById(R.id.btnCombustible);
        btnNeumaticos = (Button) findViewById(R.id.btnNeumaticos);

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
}
