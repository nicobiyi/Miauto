package com.example.nicolas.miauto.Clases;

public class Service {

    private String nombre;
    private boolean tachada;

    public Service(String nombre, boolean tachada) {
        this.nombre = nombre;
        this.tachada = tachada;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTachada() {
        return tachada;
    }

    public void setTachada(boolean tachada) {
        this.tachada = tachada;
    }


}
