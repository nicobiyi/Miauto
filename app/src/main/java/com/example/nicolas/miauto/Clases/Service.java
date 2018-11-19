package com.example.nicolas.miauto.Clases;

public class Service {
    public String getNombre() {
        return nombre;
    }

    public Service(String nombre, boolean tachada) {
        this.nombre = nombre;
        this.tachada = tachada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTachada() {
        return tachada;
    }

    public void setTachada(boolean tachada) {
        this.tachada = tachada;
    }

    private String nombre;
    private boolean tachada;
}
