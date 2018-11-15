package com.example.nicolas.miauto.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nicolas.miauto.Clases.Auto;
import com.example.nicolas.miauto.Clases.CargaCombustible;
import com.example.nicolas.miauto.Clases.Inflado;
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
        db.close();
        return auto;
    }
    //Solo Lectura - SL
    public static SQLiteDatabase verificarConexionSL(SQLiteDatabase db, Context context) {
            if (db.isOpen()){
                return db;
            } else {
                baseDatos carsHelper =  new baseDatos(context, "DBTest1", null, 1);
                db = carsHelper.getReadableDatabase();
                return db;
            }
    }
    //Lectura-Escritura - LE
    public static SQLiteDatabase verificarConexionLE(SQLiteDatabase db, Context context) {
        if (db.isOpen()){
            return db;
        } else {
            baseDatos carsHelper =  new baseDatos(context, "DBTest1", null, 1);
            db = carsHelper.getWritableDatabase();
            return db;
        }
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
        db.close();

    }

    public static void borrarEstacionamiento(SQLiteDatabase db) {
        String sql = "DELETE FROM Estacionamiento;";
        db.execSQL(sql);
        db.close();
    }

    public static boolean hayAuto(SQLiteDatabase db){
        boolean hay = false;
        // Seleccionamos todos los registros de la tabla Cars
        Cursor cursor = db.rawQuery("select * from Auto", null);

        if (cursor.getCount()!=0) {
            hay=true;
        }
        db.close();
        return hay;
    }

    public static Auto dameAuto(SQLiteDatabase db){
        Auto auto=null;
        Cursor cursor = db.rawQuery("select * from Auto", null);
        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                String patente = cursor.getString(cursor.getColumnIndex("patente"));
                String marca = cursor.getString(cursor.getColumnIndex("marca"));
                String modelo = cursor.getString(cursor.getColumnIndex("modelo"));
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                String chasis = cursor.getString(cursor.getColumnIndex("chasis"));
                String motor = cursor.getString(cursor.getColumnIndex("motor"));
                auto = new Auto(patente, marca, modelo, tipo, chasis, motor);
                cursor.moveToNext();
            }
        }
        db.close();
        return auto;
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
        bd.close();
    }

    public static void eliminarAuto(SQLiteDatabase bd) {
        String sql = "DELETE FROM Auto;";
        bd.execSQL(sql);
        sql = "DELETE FROM Estacionamiento;";
        bd.execSQL(sql);
        sql = "DELETE FROM Neumaticos;";
        bd.execSQL(sql);
        sql = "DELETE FROM Combustible;";
        bd.execSQL(sql);
        sql = "DELETE FROM Kilometraje;";
        bd.execSQL(sql);
        bd.close();
    }
    public static void eliminarDatosAuto(SQLiteDatabase bd){
        String sql = "DELETE FROM Auto;";
        bd.execSQL(sql);
        bd.close();
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
        bd.close();
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
                int idKm = cursor.getInt(cursor.getColumnIndex("id_kilometraje"));
                int kms = dameKilometraje(db, idKm);

                inflado = new Inflado(fecha, ruedadd, ruedadi, ruedatd, ruedati, ruedaau, kms);
                list.add(inflado);
                cursor.moveToNext();
            }

        }
        db.close();
        return list;
    }

    public static void crearInflado(SQLiteDatabase bd, Inflado nuevoInflado, Context context) {
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues
            if(nuevoInflado.isActualizarKm() && !existeFecha( bd, nuevoInflado.getKilometraje(), nuevoInflado.getFecha())){
                cargarKm(bd, nuevoInflado.getFecha(), nuevoInflado.getKilometraje());
            }
            bd = bdHelper.verificarConexionLE(bd, context);
            int id_km = getIdKm(bd, nuevoInflado.getKilometraje());

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
            nuevoRegistro.put("id_kilometraje", id_km);
            //Insertamos el registro en la base de datos
            bd.insert("Neumaticos", null, nuevoRegistro);
        }
        bd.close();
    }

    private static boolean existeFecha(SQLiteDatabase bd, int kilometraje, String fecha) {
        boolean existe = false;
        Cursor cursor = bd.rawQuery("select id from Kilometraje k where k.kilometros = " +  kilometraje + " and k.fecha = " + fecha, null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
               // id_km = cursor.getInt(cursor.getColumnIndex("id"));
                existe = true;
                cursor.moveToNext();
            }
        }
        return existe;
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
        db.close();
        return ids.size();
    }

    public static void guardarCargaCombustible(SQLiteDatabase bd, CargaCombustible carga, Context context) {
        //Si hemos abierto correctamente la base de datos
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues
            //regularizarHistorialCombustible(bd);
            if(carga.isActualizarKm()){
                cargarKm(bd, carga.getFecha(), carga.getKilometraje());
            }
            verificarConexionSL(bd, context);
            int id_km = getIdKm(bd, carga.getKilometraje());

            ContentValues nuevoRegistro = new ContentValues();

            // El ID es auto incrementable como declaramos en nuestro CarsSQLiteHelper

            nuevoRegistro.put("fecha", carga.getFecha());
            nuevoRegistro.put("litros", carga.getLitros());
            nuevoRegistro.put("total", carga.getTotal());
            nuevoRegistro.put("id_kilometraje", id_km);

            //Insertamos el registro en la base de datos
            bd.insert("Combustible", null, nuevoRegistro);

        }
        bd.close();
    }

    private static int getIdKm(SQLiteDatabase bd, int km) {
        int id_km = -1;
        Cursor cursor = bd.rawQuery("select id from Kilometraje k where k.kilometros = " +  km, null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                id_km = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.moveToNext();
            }
        }
        return id_km;
    }

    public static void cargarKm(SQLiteDatabase bd, String fecha, int kilometraje) {
        if (bd != null) {
            //Creamos el registro a insertar como objeto ContentValues

            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("fecha", fecha);
            nuevoRegistro.put("kilometros", kilometraje);

            //Insertamos el registro en la base de datos
            bd.insert("Kilometraje", null, nuevoRegistro);
        }
        bd.close();
    }

    public static double dameUltimaCarga(SQLiteDatabase bd) {
        double total = -1;
        Cursor cursor = bd.rawQuery("select total from Combustible order by id desc limit 1", null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                total = cursor.getInt(cursor.getColumnIndex("total"));
                cursor.moveToNext();
            }
        }
        bd.close();
        return total;
    }

    public static int dameKilometrajeMaximo(SQLiteDatabase bd) {
        int kmMaximo = -1;
        Cursor cursor = bd.rawQuery("select max(kilometros) km from Kilometraje", null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                kmMaximo = cursor.getInt(cursor.getColumnIndex("km"));
                cursor.moveToNext();
            }
        }
        bd.close();
        return kmMaximo;

    }

    public static int dameKilometraje(SQLiteDatabase bd, int id) {
        int kms = 0;
        Cursor cursor = bd.rawQuery("select kilometros from Kilometraje k where k.id = " + id, null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                kms = cursor.getInt(cursor.getColumnIndex("kilometros"));
                cursor.moveToNext();
            }
        }
        return kms;
    }



    public static List<CargaCombustible> getCargas(SQLiteDatabase db) {
        CargaCombustible carga = null;

        // Seleccionamos todos los registros de la tabla Neumaticos
        Cursor cursor = db.rawQuery("select c.fecha,c.litros, c.total, k.kilometros from Combustible c join Kilometraje k on c.id_kilometraje = k.id order by c.id desc limit 20", null);
        List<CargaCombustible> list = new ArrayList<CargaCombustible>();

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {

                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                int litros = cursor.getInt(cursor.getColumnIndex("litros"));
                int total = cursor.getInt(cursor.getColumnIndex("total"));
                int km = cursor.getInt(cursor.getColumnIndex("kilometros"));

                carga = new CargaCombustible(fecha, km, litros, total, false);
                list.add(carga);
                cursor.moveToNext();
            }

        }
        db.close();
        return list;
    }

    public static int getGastoTotalMesActual(SQLiteDatabase bd) {
        int pesosMensuales = -1;
        Cursor cursor = bd.rawQuery("select fecha, sum(total) totalMes from Kilometraje group by fecha", null);

        if (cursor.getColumnCount() > 0 && cursor.moveToFirst()) {
            // iteramos sobre el cursor de resultados,
            // y vamos rellenando el array que posteriormente devolveremos
            while (cursor.isAfterLast() == false) {
                pesosMensuales = cursor.getInt(cursor.getColumnIndex("km"));
                cursor.moveToNext();
            }
        }
        bd.close();
        return pesosMensuales;
    }


}
