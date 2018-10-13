package com.example.nicolas.miauto.Activities;

/**
 * Created by Nicolas on 4/10/2018.
 */

public class CargaCombustible {

    private String fecha;
    private int kilometraje;
    private double litros;
    private double total;



    private  boolean actualizarKm;

    public CargaCombustible(String fecha, int kilometraje, double litros, double total, boolean actualizarKM) {
        this.fecha = fecha;
        this.kilometraje = kilometraje;
        this.litros = litros;
        this.total = total;
        this.actualizarKm = actualizarKM;
    }

    public String getFecha() {
        return fecha;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public boolean isActualizarKm() {
        return actualizarKm;
    }

    public double getLitros() {
        return litros;
    }

    public double getTotal() {
        return total;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setKilometraje(int id_kilometraje) {
        this.kilometraje = id_kilometraje;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return  fecha + "  ||  " + litros + " lts  ||  Total $ " + total;
    }
}
