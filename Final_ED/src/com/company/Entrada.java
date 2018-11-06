package com.company;
public class Entrada {
    protected String nombre;
    protected long lNumeroDeAutos;

    public Entrada(String nombre, long lNumeroDeAutos){
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
