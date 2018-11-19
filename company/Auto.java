package com.company;

import java.util.Calendar;

public class Auto {
    protected long id;
    protected Hora horaEntrada, horaSalida;
    protected Calendario fechaEntrada, fechaSalida;
    protected boolean dentro;
    protected String entra;
    protected String sale;

    protected Hora horaAuxiliar;
    protected Calendario fechaAuxiliar;
    protected String sAuxiliar;
    private float velocidad, distancia;

    public Auto(long id, Hora horaEntrada, Calendario fechaEntrada, boolean dentro, String entra,
                Hora horaSalida, Calendario fechaSalida, String sale, Hora horaAuxiliar, Calendario fechaAuxiliar,
                String sAuxiliar, float velocidad, float distancia){
        this.horaEntrada = horaEntrada;
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.dentro = dentro;
        this.entra = entra;
        this.horaSalida = horaSalida;
        this.fechaSalida = fechaSalida;
        this.sale = sale;
        this.horaAuxiliar = horaAuxiliar;
        this.fechaAuxiliar = fechaAuxiliar;
        this.sAuxiliar = sAuxiliar;
        this.velocidad = velocidad;
        this.distancia = distancia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hora getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Hora horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public boolean isDentro() {
        return dentro;
    }

    public void setDentro(boolean dentro) {
        this.dentro = dentro;
    }

    public String getEntra() {
        return entra;
    }

    public void setEntra(String entra) {
        this.entra = entra;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public Calendario getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Calendario fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Hora getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Hora horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Calendario getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Calendario fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Hora duracionRecorrido(){
        Hora duracion = new Hora(0,0);
        if(horaEntrada.getHora() < horaSalida.getHora()){
            int minutosFaltantes = 60 - horaEntrada.getMinutos();
            minutosFaltantes += horaSalida.getMinutos();
            int horasFaltantes = horaSalida.getHora() - (horaEntrada.getHora() + 1);
            duracion.setHora(horasFaltantes);
            duracion.setMinutos(minutosFaltantes);
        }else if(horaEntrada.getHora() == horaSalida.getHora()){
            if(horaEntrada.getMinutos() < horaSalida.getMinutos()){
                int minutosFaltantes = horaSalida.getMinutos() - horaEntrada.getMinutos();
                duracion.setMinutos(minutosFaltantes);
            }
        }
        return duracion;
    }

    public Hora getHoraAuxiliar() {
        return horaAuxiliar;
    }

    public void setHoraAuxiliar(Hora horaAuxiliar) {
        this.horaAuxiliar = horaAuxiliar;
    }

    public Calendario getFechaAuxiliar() {
        return fechaAuxiliar;
    }

    public void setFechaAuxiliar(Calendario fechaAuxiliar) {
        this.fechaAuxiliar = fechaAuxiliar;
    }

    public String getsAuxiliar() {
        return sAuxiliar;
    }

    public void setsAuxiliar(String sAuxiliar) {
        this.sAuxiliar = sAuxiliar;
    }

    @Override
    public String toString(){
        return "ID: " + String.valueOf(id)
                + "\n" + "Hora de Entrada: " + horaEntrada.toString() + " Fecha de Entrada: " + fechaEntrada.toString()
                + "\n" + "Hora de salida: " + horaSalida.toString() + " Fecha de salida: " + fechaSalida.toString()
                + "\n" + "Entrada: " + entra + "\n" + "Salida: " + sale
                + "\n" + "Duracion recorrido: " + duracionRecorrido().toString()
                + "\n"+ "Velocidad del carro: "+ velocidad + "km/h"
                + "\n" + "Distancia: " + distancia + "km" + "\n\n";
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }
}
