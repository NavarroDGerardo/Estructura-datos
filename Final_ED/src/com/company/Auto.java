package com.company;
public class Auto {
    protected long id;
    protected Hora hora;
    protected Calendario fecha;
    protected boolean dentro;
    protected String entra;
    protected String sale;

    public Auto(long id, Hora hora, Calendario fecha, boolean dentro, String entra, String sale){
        this.hora = hora;
        this.id = id;
        this.fecha = fecha;
        this.dentro = dentro;
        this.entra = entra;
        this.sale = sale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Hora getHora() {
        return hora;
    }

    public void setHora(Hora hora) {
        this.hora = hora;
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

    public Calendario getFecha() {
        return fecha;
    }

    public void setFecha(Calendario fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString(){
        return "ID: " + String.valueOf(id) + "\n" + "Hora de Entrada: " + hora.toString() + " Fecha de Entrada: "
                + fecha.toString() + "\n" + "Entrada: " + entra + "\n" + "Salida: " + sale + "\n";
    }
}
