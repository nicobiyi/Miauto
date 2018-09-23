package com.example.nicolas.miauto.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class bdHelper {


    public static LatLng getEstacionamiento(SQLiteDatabase db) {
        LatLng auto = null;
        // Seleccionamos todos los registros de la tabla Cars
        Cursor cursor = db.rawQuery("select * from Estacionamiento", null);
        List<LatLng> list = new ArrayList<LatLng>();

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {

                double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                double lon = cursor.getDouble(cursor.getColumnIndex("lon"));

                list.add(new LatLng(lat,lon));
                cursor.moveToNext();
            }
            auto = list.get(0);
        }
        return auto;
    }

    public static void guardarDireccion(SQLiteDatabase db, LatLng loc) {
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues nuevoRegistro = new ContentValues();
            // El ID es auto incrementable como declaramos en nuestro CarsSQLiteHelper
            nuevoRegistro.put("lat", loc.latitude);
            nuevoRegistro.put("lon", loc.longitude);

            //Insertamos el registro en la base de datos
            db.insert("Estacionamiento", null, nuevoRegistro);
        }
    }

    public static void borrarEstacionamiento(SQLiteDatabase db) {
        String sql = "DELETE FROM Estacionamiento;";
        db.execSQL(sql);
    }

    public static boolean hayAuto(SQLiteDatabase db){
        boolean hay = false;
        // Seleccionamos todos los registros de la tabla Cars
        Cursor cursor = db.rawQuery("select * from Auto", null);

        if (cursor.getCount()!=0) {
            hay=true;
        }
    return hay;
    }

    public static void crearAuto(SQLiteDatabase bd, Auto auto) {
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues nuevoRegistro = new ContentValues();
            // El ID es auto incrementable como declaramos en nuestro CarsSQLiteHelper
            nuevoRegistro.put("patente", auto.getPatente());
            nuevoRegistro.put("marca", auto.getMarca());
            nuevoRegistro.put("modelo", auto.getModelo());
            nuevoRegistro.put("tipo", auto.getTipo());
            nuevoRegistro.put("chasis", auto.getChasis());
            nuevoRegistro.put("motor", auto.getMotor());

            //Insertamos el registro en la base de datos
            bd.insert("Auto", null, nuevoRegistro);
        }
    }

    public static void eliminarAuto(SQLiteDatabase bd) {
        String sql = "DELETE FROM Auto;";
        bd.execSQL(sql);
        sql = "DELETE FROM Estacionamiento;";
        bd.execSQL(sql);
    }

    public static String damePatente(SQLiteDatabase bd){
        String patente="";
        Cursor cursor = bd.rawQuery("select patente from Auto", null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {

                patente = cursor.getString(cursor.getColumnIndex("patente"));
                cursor.moveToNext();
            }
        }
        return patente;
    }
}
