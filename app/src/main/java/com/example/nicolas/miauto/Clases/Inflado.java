package com.example.nicolas.miauto.Clases;

public class Inflado {

    private String fecha;
    private int ruedadd;
    private int ruedadi;
    private int ruedatd;
    private int ruedati;
    private int ruedaau;
    private int kilometraje;
    private  boolean actualizarKm;

    public Inflado(String fecha, int ruedadd, int ruedadi, int ruedatd, int ruedati, int ruedaau, int kilometraje, boolean actualizarKm ) {
        this.fecha = fecha;
        this.ruedadd = ruedadd;
        this.ruedadi = ruedadi;
        this.ruedatd = ruedatd;
        this.ruedati = ruedati;
        this.ruedaau = ruedaau;
        this.kilometraje = kilometraje;
        this.actualizarKm = actualizarKm;
    }

    public Inflado(String fecha, int ruedadd, int ruedadi, int ruedatd, int ruedati, int ruedaau, int kilometraje) {
        this.fecha = fecha;
        this.ruedadd = ruedadd;
        this.ruedadi = ruedadi;
        this.ruedatd = ruedatd;
        this.ruedati = ruedati;
        this.ruedaau = ruedaau;
        this.kilometraje = kilometraje;
        this.actualizarKm = false;
    }


    public String getFecha() {return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getRuedadd() {
        return ruedadd;
    }

    public void setRuedadd(int ruedadd) {
        this.ruedadd = ruedadd;
    }

    public int getRuedadi() {
        return ruedadi;
    }

    public void setRuedadi(int ruedadi) {
        this.ruedadi = ruedadi;
    }

    public int getRuedatd() {
        return ruedatd;
    }

    public void setRuedatd(int ruedatd) {
        this.ruedatd = ruedatd;
    }

    public int getRuedati() {
        return ruedati;
    }

    public void setRuedati(int ruedati) {
        this.ruedati = ruedati;
    }

    public int getRuedaau() {
        return ruedaau;
    }

    public void setRuedaau(int ruedaau) {
        this.ruedaau = ruedaau;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int idKm) {
        this.kilometraje = idKm;
    }

    public boolean isActualizarKm() {
        return actualizarKm;
    }

    public void setActualizarKm(boolean actualizarKm) {
        this.actualizarKm = actualizarKm;
    }
}
