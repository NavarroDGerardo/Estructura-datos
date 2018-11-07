package com.company;
public class Auto {
    protected long id;
    protected Hora horaEntrada, horaSalida;
    protected Calendario fechaEntrada, fechaSalida;
    protected boolean dentro;
    protected String entra;
    protected String sale;

    public Auto(long id, Hora horaEntrada, Calendario fechaEntrada, boolean dentro, String entra,
                Hora horaSalida, Calendario fechaSalida, String sale){
        this.horaEntrada = horaEntrada;
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.dentro = dentro;
        this.entra = entra;
        this.horaSalida = horaSalida;
        this.fechaSalida = fechaSalida;
        this.sale = sale;
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

    @Override
    public String toString(){
        return "ID: " + String.valueOf(id)
                + "\n" + "Hora de Entrada: " + horaEntrada.toString() + " Fecha de Entrada: " + fechaEntrada.toString()
                + "\n" + "Hora de salida: " + horaSalida.toString() + " Fecha de salida: " + fechaSalida.toString()
                + "\n" + "Entrada: " + entra + "\n" + "Salida: " + sale
                + "\n" + "Duracion recorrido: " + duracionRecorrido().toString() + "\n\n";
    }
}
