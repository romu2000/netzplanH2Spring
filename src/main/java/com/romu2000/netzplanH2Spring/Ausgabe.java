package com.romu2000.netzplanH2Spring;

import com.romu2000.netzplanH2Spring.model.Knoten;
import java.util.LinkedList;

public class Ausgabe {
    Service service = new Service();

    public String ausgabeKritischerPfadBootStrap(){
        String myKritischerPfad;
        StringBuilder str = new StringBuilder("Kritischer Pfad: ");
        LinkedList<Integer> kritPfad = service.kritischerPfad(new Knoten());
        for(Object i : kritPfad){
            if(i.equals(0)){
                str.append(i);
            }else{
                str.append(" --> " +i);
            }
        }
        return myKritischerPfad = str.toString();

    }
}