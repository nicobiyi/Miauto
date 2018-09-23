package com.example.nicolas.miauto.Activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class baseDatos extends SQLiteOpenHelper {

    String estacionamiento = "CREATE TABLE Estacionamiento (lat double, lon double);";
    String auto = "CREATE TABLE Auto (\n" +
            "  patente varchar(10) NOT NULL,\n" +
            "  marca varchar(30),\n" +
            "  modelo varchar(30),\n" +
            "  tipo varchar(30),\n" +
            "  chasis varchar(30),\n" +
            "  motor varchar(30)\n" +
            ");";

    public baseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(estacionamiento);
        db.execSQL(auto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Estacionamiento");
        db.execSQL("DROP TABLE IF EXISTS Auto");


        //Se crea la nueva versión de la tabla
        db.execSQL(estacionamiento);
        db.execSQL(auto);
    }
}