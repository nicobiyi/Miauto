package com.example.nicolas.miauto.Genericos;

import java.util.Calendar;

public class Fechador {

    public static String dameFechaActual(){
        String fecha;
        Calendar calendar =  Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        mes++;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = dia + "/" + mes + "/" + anio;
        return  fecha;
    }
}
