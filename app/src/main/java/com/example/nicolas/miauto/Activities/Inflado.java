package com.example.nicolas.miauto.Activities;

public class Inflado {

    public Inflado(String fecha, int ruedadd, int ruedadi, int ruedatd, int ruedati, int ruedaau) {
        this.fecha = fecha;
        this.ruedadd = ruedadd;
        this.ruedadi = ruedadi;
        this.ruedatd = ruedatd;
        this.ruedati = ruedati;
        this.ruedaau = ruedaau;
    }

    String fecha;
    int ruedadd;
    int ruedadi;
    int ruedatd;
    int ruedati;
    int ruedaau;

    public String getFecha() {
        return fecha;
    }

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
}
