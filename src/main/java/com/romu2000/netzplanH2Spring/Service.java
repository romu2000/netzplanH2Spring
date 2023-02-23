package com.romu2000.netzplanH2Spring;
import com.romu2000.netzplanH2Spring.controller.KnotenController;
import com.romu2000.netzplanH2Spring.model.Knoten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Service extends KnotenController {
    private Knoten vorgaenger(int m, int y){
        return knotenListe.get(knotenListe.get(m).getVorgaenger().get(y)-1);
    }
    private Knoten knoten(int i){

        return knotenListe.get(i);

    }
    private int vorgaengerIdNull(int i){
        return knoten(i).getVorgaenger().get(0);
    }

    public int projektDauerGesamt(){
        return knotenListe.get(knotenListe.size()-1).getFez();
    }

    //Berechnung von FAZ und FEZ
    private void calcFazFez(ArrayList<Knoten> knotenListe) {
        for (int i = 0; i < knotenListe.size(); i++) {
            if (knoten(i).getVorgaenger().get(0) == 0) { //Startknoten FAZ=0 setzen
                knoten(i).setFaz(0);
                knoten(i).setFez(knoten(i).getDauer());
            }
            for(int y = 0; y < knoten(i).getVorgaenger().size() ; y++) {  //Durch Liste von Vorgängern iterieren
                if(knoten(i).getVorgaenger().get(0) != 0){
                    knoten(i).setFaz(vorgaenger(i,y).getFez());
                    knoten(i).setFez(knoten(i).getFaz() + knoten(i).getDauer());
                    for(int x = 0; x <= knoten(i).getVorgaenger().size()-1 ; x++){  //prüfen des größten FEZ und Übergabe an FAZ
                        if(vorgaenger(i,x).getFez() > knoten(i).getFaz()){
                            knoten(i).setFaz(vorgaenger(i,x).getFez());
                            knoten(i).setFez(knoten(i).getFaz() + knoten(i).getDauer());
                        }
                    }
                }
            }
        }
    }
    //Berechnung von SAZ und SEZ
    private void calcSazSez(ArrayList<Knoten> knotenListe) {
        for (int i = knotenListe.size() - 1; i >= 0; i--) {
            Knoten knotenAkt = knotenListe.get(i);
            int vorgaengerId = knotenAkt.getVorgaenger().get(0);
            if (i == knotenListe.size() - 1) {  //letzten Knoten SEZ = FEZ setzen
                knotenAkt.setSez(knotenAkt.getFez());
                knotenAkt.setSaz(knotenAkt.getSez() - knotenAkt.getDauer());
                vorgaenger(i,0).setSez(knotenAkt.getSaz());   //Vorgaenger mit SAZ auf SEZ setzen und gleichzeit den SAZ des Vorgängers berechnen
                vorgaenger(i,0).setSaz(knotenListe.get(vorgaengerId - 1).getSez() - knotenListe.get(vorgaengerId - 1).getDauer());
            } else {
                if (vorgaengerId != 0) {
                    Knoten meinVorgaenger = knotenListe.get(vorgaengerId - 1);
                    //Prüfe mehrere Vorgänger
                    if(meinVorgaenger.getSaz() != 0){
                    }else{
                        if (knotenAkt.getVorgaenger().size() >= 1) {
                            for (int y = 0; y <= knotenAkt.getVorgaenger().size() - 1; y++) {  // durch alle Vorgänger iterieren
                                vorgaengerId = knotenAkt.getVorgaenger().get(y);
                                meinVorgaenger = knotenListe.get(vorgaengerId - 1);
                                meinVorgaenger.setSez(knotenAkt.getSaz());
                                meinVorgaenger.setSaz(meinVorgaenger.getSez() - meinVorgaenger.getDauer());
                            }
                        }
                    }

                }
            }
        }
    }
    private void doppelteKnoten(ArrayList<Knoten> knotenListe) { //prüfen auf doppelte Nachfolger und kleinsten SAZ auf SEZ setzen
        for (int i = knotenListe.size() - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                Knoten knotenAkt = knotenListe.get(i);
                int vorgaengerId = knotenAkt.getVorgaenger().get(0);
                if (vorgaengerIdNull(i) != 0) {
                    if (knotenAkt.getVorgaenger().equals(knotenListe.get(j).getVorgaenger())) {
                        if (knotenAkt.getSez() > knotenListe.get(vorgaengerId - 1).getSaz()) {
                            knotenListe.get(vorgaengerId - 1).setSez(knotenListe.get(i).getSaz());
                        }
                        Knoten meinVorgaenger = knotenListe.get(vorgaengerId - 1);
                        meinVorgaenger.setSaz(meinVorgaenger.getSez() - meinVorgaenger.getDauer());
                        meinVorgaenger.setSez(knotenAkt.getSaz());
                    }
                }
            }
        }
    }
    // Berechnung größter Puffer
    private void berechneGp(ArrayList<Knoten> knotenliste){
        for(Knoten knoten: knotenliste){
            int getGp = knoten.getSaz()- knoten.getFaz();
            knoten.setgP(getGp);
        }
    }
    // Berechnung des freien Puffers
    private void berechneFp(){
        for(int i = 0; i <= knotenListe.size()-1; i++){
            for(int y = 0; y <= knoten(i).getVorgaenger().size()-1 ; y++) {
                if(knoten(i).getVorgaenger().get(0) != 0){
                    int fP = knoten(i).getFaz() - vorgaenger(i,y).getFez();
                    vorgaenger(i,y).setfP(fP);
                }
            }
        }
    }
    //Ausgabe des kritischen Pfads als LinkedList
    public LinkedList<Integer> kritischerPfad(Knoten knoten) {
        LinkedList<Integer> kritischerPfadListe = new <Integer>LinkedList<Integer>();
        for (Knoten k : knotenListe) {
            if (k.getgP() == 0) {
                kritischerPfadListe.add(k.getId());
            }
            Collections.sort(kritischerPfadListe);
        }
        return kritischerPfadListe;
    }
    //Alle Berechnungsmethoden in eine packen
    public void allesBerechnen(){
        calcFazFez(knotenListe);
        if(knotenListe.size()>1){
            calcSazSez(knotenListe);
            doppelteKnoten(knotenListe); //Nachfolger berechnen
            berechneFp();
            berechneGp(knotenListe);
        }
    }
}