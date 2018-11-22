package com.example.nicolas.miauto.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Fragments.HistorialInflado;
import com.example.nicolas.miauto.Fragments.NuevoInflado;
import com.example.nicolas.miauto.R;

public class NeumaticosActivity extends Activity {

    //importamos la clase fragment y creamos objetos
    Fragment currentFragment;
    private FragmentManager supportFragmentManager;
    private static baseDatos carsHelper;
    private static SQLiteDatabase bd;


    //si ya existe una instancia, no crea una nueva
    //esto es para cuando salimos de la app sin cerrarla
    //q no vuelva a cargar la vista inicial
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neumaticos);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getReadableDatabase();
        setTitle("Neumáticos - " + bdHelper.damePatente(bd));


        if (savedInstanceState == null) {
            currentFragment = new NuevoInflado();
            changeFragment(currentFragment);
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //usa el menu que creamos en el layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_neumaticos, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //dependiendo la opcion le asigna al fragment una clase
    //crear un menu en res/menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itNuevoInflado:
                currentFragment = new NuevoInflado();
                break;
            case R.id.itHistorial:
                currentFragment = new HistorialInflado();
                break;
            case android.R.id.home :
                Intent intent = new Intent(NeumaticosActivity.this, MenuActivity.class);
                startActivity(intent);
        }
        changeFragment(currentFragment);
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }



}
