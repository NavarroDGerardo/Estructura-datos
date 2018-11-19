package com.company;

public class Hora implements Comparar<Hora>{
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

    @Override
    public boolean esMayor(Hora review){
        int min = review.getMinutos();
        int ho = review.getHora();
        if(this.hora > ho){
            return true;
        }else{
            if(this.minutos > min){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean esIgual(Hora review) {
        int min = review.getMinutos();
        int ho = review.getHora();
        if(this.hora == ho && this.minutos == min){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean esMenor(Hora review) {
        int min = review.getMinutos();
        int ho = review.getHora();
        if(this.hora < ho){
            return true;
        }else{
            if(this.minutos < min){
                return true;
            }
        }
        return false;
    }
}
