package com.company;
public class Salida {
    protected String nombre;
    protected long lNumeroDeAutos;

    public Salida(String nombre, long lNumeroDeAutos){
        this.nombre = nombre;
        this.lNumeroDeAutos = lNumeroDeAutos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getlNumeroDeAutos() {
        return lNumeroDeAutos;
    }

    public void setlNumeroDeAutos(long lNumeroDeAutos) {
        this.lNumeroDeAutos = lNumeroDeAutos;
    }
}
