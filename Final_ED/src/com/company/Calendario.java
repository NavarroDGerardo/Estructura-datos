package com.company;
public class Calendario {
    private int Dia, Mes, año;

    public Calendario(){
        Dia = 1;
        Mes = 1;
        año = 2018;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    @Override
    public String toString() {
        return String.valueOf(Dia) + " / " + String.valueOf(Mes) + " / " + String.valueOf(año);
    }
}
