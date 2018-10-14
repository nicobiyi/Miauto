package com.example.nicolas.miauto.Clases;

public class Auto {
    private String patente;
    private String marca;
    private String modelo;
    private String tipo;
    private String chasis;
    private String motor;


    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getChasis() {
        return chasis;
    }

    public String getMotor() {
        return motor;
    }

    public Auto(String patente, String marca, String modelo, String tipo, String chasis, String motor){
        this.patente=patente;
        this.marca=marca;
        this.modelo=modelo;
        this.tipo=tipo;
        this.chasis=chasis;
        this.motor=motor;

    }

}
