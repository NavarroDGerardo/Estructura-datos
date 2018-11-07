package com.company;

public class Hora {
    private int hora, minutos;

    public Hora(){
        hora = 00;
        minutos = 00;
    }

    public Hora(int hora, int minutos){
        this.hora = hora;
        this.minutos = minutos;
    }


    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    @Override
    public String toString(){
        return String.valueOf(hora) + ":" + String.valueOf(minutos);
    }
}
