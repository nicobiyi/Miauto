package com.example.nicolas.miauto.Activities;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nicolas.miauto.BaseDeDatos.baseDatos;
import com.example.nicolas.miauto.BaseDeDatos.bdHelper;
import com.example.nicolas.miauto.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private ImageButton btnBorrar;
    private ImageButton btnGuardar;
    private List<Address> direcciones;
    private Geocoder geocoder;
    private Marker autoMarker;
    private CameraPosition camera;
    private LocationManager locationManager;
    private Location currentLocation;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int ZOOM = 16;
    private static final int BEARING = 0;
    private static final int TILT = 30;
    private static final String ESTACIONADO = "Estacionado en:";
    private LocationListener locationListener;
    private LatLng latLongAuto;
    private LatLng temp;
    private int cont = 0;

    private static baseDatos carsHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        carsHelper = new baseDatos(getApplicationContext(), "DBTest1", null, 1);
        db = carsHelper.getWritableDatabase();
        setTitle("Mapa Auto - " + bdHelper.damePatente(db));
        btnGuardar = (ImageButton) findViewById(R.id.btnGuardar);
        btnBorrar = (ImageButton) findViewById(R.id.btnBorrar);
        if (!checkGPS()) {
            encenderGPS();
        }
        //ubicarme con el gps
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                if (cont==0){
                    temp = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, 16.0f));
                    cont=-1;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGuardar.setVisibility(View.INVISIBLE);
                btnBorrar.setVisibility(View.VISIBLE);
                guardarDireccion(currentLocation);
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGuardar.setVisibility(View.VISIBLE);
                btnBorrar.setVisibility(View.INVISIBLE);
                borrarDireccion();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
        startActivity(intent);
        return true;
    }

    private void ubicame() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = bdHelper.verificarConexionSL(db, MapsActivity.this);
        if (!bdHelper.hayAuto(db)){
            Intent intent = new Intent(MapsActivity.this, GarageActivity.class);
            startActivity(intent);
        }
        if (!checkGPS()) {
            encenderGPS();
        }
    }

    private void encenderGPS() {
        new AlertDialog.Builder(this)
                .setTitle("GPS")
                .setMessage("No tiene el GPS habilitado. Desea habilitarlo?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!checkGPS()) {
                            Toast.makeText(getApplicationContext(), "No puede continuar sin el GPS encendido", Toast.LENGTH_LONG).show();
                            encenderGPS();
                        }
                    }
                })

                .show();
    }

    private boolean checkGPS() {
        try {
            int gpsSignal = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(getApplicationContext(), "ERROR DE GPS", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        ubicame();

        gmap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                gmap.clear();
                latLongAuto = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                autoMarker = gmap.addMarker(new MarkerOptions()
                        .position(latLongAuto).title(ESTACIONADO).draggable(true));
                autoMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
                setSnipet(autoMarker);
                zoomToLocation(autoMarker);
                guardarMarcador(latLongAuto.latitude, latLongAuto.longitude);
            }
        });
        gmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                gmap.clear();
                latLongAuto = latLng;
                autoMarker = gmap.addMarker(new MarkerOptions()
                        .position(latLongAuto).title(ESTACIONADO).draggable(true));
                autoMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
                setSnipet(autoMarker);
                zoomToLocation(autoMarker);
                guardarMarcador(latLng.latitude, latLng.longitude);
                btnGuardar.setVisibility(View.INVISIBLE);
                btnBorrar.setVisibility(View.VISIBLE);
            }
        });
        inicializar();

    }

    private void guardarMarcador(double lat, double lon) {
        db = bdHelper.verificarConexionLE(db, MapsActivity.this);
        bdHelper.borrarEstacionamiento(db);
        LatLng temp = new LatLng(lat, lon);
        db = bdHelper.verificarConexionLE(db, MapsActivity.this);
        bdHelper.guardarDireccion(db, temp);
    }

    private void inicializar() {
        db = bdHelper.verificarConexionSL(db, MapsActivity.this);
        latLongAuto = bdHelper.getEstacionamiento(db);
        if (latLongAuto!=null){
            autoMarker = gmap.addMarker(new MarkerOptions()
                    .position(latLongAuto).title(ESTACIONADO).draggable(true));
            autoMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
            setSnipet(autoMarker);
            btnGuardar.setVisibility(View.INVISIBLE);
            btnBorrar.setVisibility(View.VISIBLE);
        }
    }


    private void borrarDireccion() {
        //borro de la bd
        db = bdHelper.verificarConexionLE(db, MapsActivity.this);
        bdHelper.borrarEstacionamiento(db);
        gmap.clear();
        Toast.makeText(getApplicationContext(),"Ubicación eliminada", Toast.LENGTH_LONG).show();
    }

    private void guardarDireccion(Location loc) {
        //guardo en la bd
        LatLng temp = new LatLng(loc.getLatitude(), loc.getLongitude());
        db = bdHelper.verificarConexionLE(db, MapsActivity.this);
        bdHelper.guardarDireccion(db, temp);
        latLongAuto= new LatLng(loc.getLatitude(),loc.getLongitude());
        autoMarker = gmap.addMarker(new MarkerOptions()
                .position(latLongAuto).title(ESTACIONADO).draggable(true));
        autoMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
        setSnipet(autoMarker);
        Toast.makeText(getApplicationContext(),"Ubicación guardada", Toast.LENGTH_LONG).show();
    }

    private void setSnipet(Marker marker) {
        String dir;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            direcciones = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            dir = direcciones.get(0).getAddressLine(0);

            marker.setSnippet(dir);
            marker.showInfoWindow();
        } catch (IOException e) {
            dir = "Dirección no disponible!";
            marker.setSnippet(dir);
            marker.showInfoWindow();
        }
    }
    private void zoomToLocation(Marker marker) {
        camera = new CameraPosition.Builder()
                .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                .zoom(ZOOM)           // limit -> 21
                .bearing(BEARING)         // 0 - 365º
                .tilt(TILT)           // limit -> 90
                .build();
        gmap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }

}
