package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Clases.CustomAdapter;
import com.example.nicolas.miauto.Clases.Service;
import com.example.nicolas.miauto.R;

import java.util.List;

public class ServiceActivity extends Activity {
    private ListView lvListaCargas;
    private List<Service> listaService;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;
    ImageButton btnAgregar;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        lvListaCargas = (ListView) findViewById(R.id.lvService);
        btnAgregar = (ImageButton)findViewById(R.id.btnAgregarService);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getReadableDatabase();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        listaService = generarArray(bd);
        customAdapter = new CustomAdapter(getApplicationContext(), listaService);

        lvListaCargas.setAdapter(customAdapter);
        bd = carsHelper.getReadableDatabase();
        setTitle("Servicios - " + bdHelper.damePatente(bd));

        lvListaCargas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bd=bdHelper.verificarConexionLE(bd, ServiceActivity.this);
                customAdapter.tachar(position, view, bd);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                damePalabra();
            }
        });
        lvListaCargas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                borrarPalabra(position,view);
                return false;
            }
        });
    }

    private void borrarPalabra(int position, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
        builder.setMessage("Seguro que desea borrar?")
                .setTitle("Atención!");

        final int posi = position;
        final View v = view;

        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String  p = customAdapter.borrarPalabra(posi, v);
                bd = bdHelper.verificarConexionLE(bd, ServiceActivity.this);
                bdHelper.borrarPalabra(bd, p);
                customAdapter.notifyDataSetChanged();
                Toast.makeText(ServiceActivity.this,"Service eliminado", Toast.LENGTH_LONG).show();
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
        dialog.show();



    }

    private String damePalabra() {
        final String[] palabrita = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialogservice, null);
        builder.setView(viewInflated);
        final EditText input = (EditText) viewInflated.findViewById(R.id.dialogService);
        input.requestFocus();
        builder.setMessage("Ingrese la palabra:")
                .setTitle("Nuevo item");

        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.length()!=0){
                    palabrita[0] = input.getText().toString();
                    customAdapter.agregarPalabra(palabrita[0]);
                    customAdapter.notifyDataSetChanged();
                    bd = bdHelper.verificarConexionLE(bd,ServiceActivity.this);
                    bdHelper.agregarServicio(palabrita[0], bd);
                    Toast.makeText(ServiceActivity.this,"Service ingresado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    damePalabra();
                    Toast.makeText(ServiceActivity.this,"Debe ingresar una palabra", Toast.LENGTH_LONG).show();

                }
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
        dialog.show();
        return palabrita[0];
    }

    private List<Service> generarArray(SQLiteDatabase bd) {
        bd = bdHelper.verificarConexionSL(bd, ServiceActivity.this);
        listaService = bdHelper.dameServices(bd);
        return listaService;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ServiceActivity.this, MenuActivity.class);
        startActivity(intent);
        return true;
    }
}
