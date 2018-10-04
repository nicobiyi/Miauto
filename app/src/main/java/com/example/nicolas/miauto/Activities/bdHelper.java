package com.example.nicolas.miauto.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        sql = "DELETE FROM Neumaticos;";
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

    public static List<Inflado>  getNeumaticos(SQLiteDatabase db) {
        Inflado inflado = null;

        // Seleccionamos todos los registros de la tabla Neumaticos
        Cursor cursor = db.rawQuery("select * from Neumaticos", null);
        List<Inflado> list = new ArrayList<Inflado>();

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {

                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                int ruedadd = cursor.getInt(cursor.getColumnIndex("ruedadd"));
                int ruedadi = cursor.getInt(cursor.getColumnIndex("ruedadi"));
                int ruedatd = cursor.getInt(cursor.getColumnIndex("ruedatd"));
                int ruedati = cursor.getInt(cursor.getColumnIndex("ruedati"));
                int ruedaau = cursor.getInt(cursor.getColumnIndex("ruedaau"));

                inflado = new Inflado(fecha, ruedadd, ruedadi, ruedatd, ruedati, ruedaau);
                list.add(inflado);
                cursor.moveToNext();
            }

        }
        return list;
    }

    public static void crearInflado(SQLiteDatabase bd, Inflado nuevoInflado) {
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues
           regularizarHistorial(bd);
            ContentValues nuevoRegistro = new ContentValues();
            // El ID es auto incrementable como declaramos en nuestro CarsSQLiteHelper
            //nuevoRegistro.put("id", 0);
            nuevoRegistro.put("fecha", nuevoInflado.getFecha());
            nuevoRegistro.put("ruedadd", nuevoInflado.getRuedadd());
            nuevoRegistro.put("ruedadi", nuevoInflado.getRuedadi());
            nuevoRegistro.put("ruedatd", nuevoInflado.getRuedatd());
            nuevoRegistro.put("ruedati", nuevoInflado.getRuedati());
            nuevoRegistro.put("ruedaau", nuevoInflado.getRuedaau());

            //Insertamos el registro en la base de datos
            bd.insert("Neumaticos", null, nuevoRegistro);
        }
    }

    private static void regularizarHistorial(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select id from Neumaticos", null);
        List<Integer> ids = new ArrayList<Integer>();


        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                ids.add(cursor.getInt(cursor.getColumnIndex("id")));
                cursor.moveToNext();
            }
        }
       if(ids.size() ==3){
          db.delete ("Neumaticos", "id = "+ ids.get(0).toString(), null);
       }
    }
    public static int contarInflados (SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select id from Neumaticos", null);
        List<Integer> ids = new ArrayList<Integer>();


        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                ids.add(cursor.getInt(cursor.getColumnIndex("id")));
                cursor.moveToNext();
            }
        }
        return ids.size();
    }


}
