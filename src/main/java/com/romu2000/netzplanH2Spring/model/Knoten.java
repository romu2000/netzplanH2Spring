package com.romu2000.netzplanH2Spring.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "knoten")
@Table(name = "tbl_knot")
@NamedQuery(
        name = "Knoten.findOhneId",
        query = "SELECT k from knoten k"
)
public class Knoten {


    public Knoten() {
    }

    @Autowired
    public Knoten(int nr, String name, String beschr, int dauer, List<Integer> vorgaenger) {
        this.nr = nr;
        this.name = name;
        this.beschr = beschr;
        this.dauer = dauer;
        this.vorgaenger = vorgaenger;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int nr;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(length = 100)
    private String beschr;

    @Column(length = 10)
    private int dauer;

    @ElementCollection
    @CollectionTable(name = "tbl_VORG", joinColumns = @JoinColumn(name = "KID"))
    @Column(name = "VID")
    private List<Integer> vorgaenger;

    // WORKAROUND zur Berechnung der Knoten -- unschöne Lösung
    @Transient
    private int faz;
    @Transient
    private int fez;
    @Transient
    private int sez;
    @Transient
    private int saz;
    @Transient
    private int gP;
    @Transient
    private int fP;

    public int getFaz() {
        return faz;
    }

    public void setFaz(int faz) {
        this.faz = faz;
    }

    public int getFez() {
        return fez;
    }

    public void setFez(int fez) {
        this.fez = fez;
    }

    public int getSez() {
        return sez;
    }

    public void setSez(int sez) {
        this.sez = sez;
    }

    public int getSaz() {
        return saz;
    }

    public void setSaz(int saz) {
        this.saz = saz;
    }

    public int getgP() {
        return gP;
    }

    public void setgP(int gP) {
        this.gP = gP;
    }

    public int getfP() {
        return fP;
    }

    public void setfP(int fP) {
        this.fP = fP;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschr() {
        return beschr;
    }

    public void setBeschr(String beschr) {
        this.beschr = beschr;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    @Override
    public String toString() {
        return "Knoten{" +
                "id=" + id +
                ", nr=" + nr +
                ", name='" + name + '\'' +
                ", beschr='" + beschr + '\'' +
                ", vorgaenger='" + vorgaenger + '\'' +
                ", dauer=" + dauer +
                '}';
    }

    public List<Integer> getVorgaenger() {
        return vorgaenger;
    }

    public void setVorgaenger(List<Integer> newVorgaenger) {

        this.vorgaenger = new ArrayList<>(newVorgaenger);
    }


}


/*
public record Knoten(@Id Integer id,Integer nr, String name, String beschreibung){

}*/
