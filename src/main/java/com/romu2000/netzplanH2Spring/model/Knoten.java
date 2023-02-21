package com.romu2000.netzplanH2Spring.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "knoten")
@Table(name = "tbl_knoten")
public class Knoten {
    public Knoten() {
    }

    public Knoten(Integer nr, String name, String beschr, Integer dauer, List<Integer> vorgaenger) {
        this.nr = nr;
        this.name = name;
        this.beschr = beschr;
        this.dauer = dauer;
        this.vorgaenger = vorgaenger;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer nr;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(length = 100)
    private String beschr;

    @Column(length = 10)
    private Integer dauer;

    @ElementCollection
    @CollectionTable(name = "tbl_VORGAENGER", joinColumns = @JoinColumn(name = "KID"))
    @Column(name = "VID")
    private List<Integer> vorgaenger;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNr() {
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

    public Integer getDauer() {
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
