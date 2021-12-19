package com.example.emploiandroid.Models;

import java.util.Date;

public class Seance {
    private  int id;
    private Date DateDebut;
    private Date DateFin;
    private int NbrRep;
    public Seance(){}
    public Seance(int id, Date dateDebut, Date dateFin, int nbrRep) {
        this.id = id;
        this.DateDebut = dateDebut;
        this.DateFin = dateFin;
        this.NbrRep = nbrRep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }

    public int getNbrRep() {
        return NbrRep;
    }

    public void setNbrRep(int nbrRep) {
        NbrRep = nbrRep;
    }

}
