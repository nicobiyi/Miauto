package com.example.nicolas.miauto.Activities;


import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.Fragments.Tab1Carga;
import com.example.nicolas.miauto.Fragments.Tab2DetallesCargas;
import com.example.nicolas.miauto.Fragments.Tab3Estadisticas;
import com.example.nicolas.miauto.R;

public class CombustibleActivity extends Activity implements ActionBar.TabListener {
   private SQLiteDatabase bd;
    private static baseDatos carsHelper;
    private ViewPager mViewPager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustible);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        bd = carsHelper.getReadableDatabase();
        setTitle("Combustible - " + bdHelper.damePatente(bd));

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        int vieneDelIntent = getIntent().getIntExtra("Cargas", 1);
        if ( vieneDelIntent == 1){
            getRoberto();
        }


    }

    private Fragment getRoberto() {
        Tab2DetallesCargas tab2 = new Tab2DetallesCargas();
        return tab2;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_combustible, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case 16908332:
                Intent intent = new Intent(CombustibleActivity.this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                eliminarCargasCombustible();

                break;
        }
        return true;
    }

    private void eliminarCargasCombustible() {
        baseDatos carsHelper= new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        bd = carsHelper.getReadableDatabase();
        AlertDialog.Builder builder = new AlertDialog.Builder(CombustibleActivity.this);
        builder.setMessage("Está seguro que desea borrar las cargas de combustible?")
                .setTitle("Patente: " + bdHelper.damePatente(bd));

        builder.setCancelable(true);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bd = bdHelper.verificarConexionLE(bd, CombustibleActivity.this);
                bdHelper.borrarCargasCombustible(bd);
                Toast.makeText(getApplicationContext(),"Cargas de combustible eliminadas con éxito", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), CombustibleActivity.class);
                intent.putExtra("Cargas", 1);
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
        dialog.show();

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    Tab1Carga tab1 = new Tab1Carga();
                    return tab1;
                case 1:
                    Tab2DetallesCargas tab2 = new Tab2DetallesCargas();
                    return tab2;
                case 2:
                    Tab3Estadisticas tab3 = new Tab3Estadisticas();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Surtidor";
                case 1:
                    return "Cargas";
                case 2:
                    return "Estadistica";
            }
            return null;
        }
    }
}
