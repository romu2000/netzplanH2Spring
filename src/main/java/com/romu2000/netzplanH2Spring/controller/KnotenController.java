package com.romu2000.netzplanH2Spring.controller;

import com.romu2000.netzplanH2Spring.Ausgabe;
import com.romu2000.netzplanH2Spring.Service;
import com.romu2000.netzplanH2Spring.model.Knoten;
import com.romu2000.netzplanH2Spring.service.KnotenNotFoundException;
import com.romu2000.netzplanH2Spring.service.KnotenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class KnotenController {

    public static ArrayList<Knoten> knotenListe = new ArrayList<>();
    @Autowired
    private KnotenService kService;
    @GetMapping("/knoten")
    public String showKnotenList(Model model){
        knotenListe.clear();
        Ausgabe ausgabe = new Ausgabe();
        Service service = new Service();
        System.out.println("SQL ABFRAGE SIZE:" + kService.listAllKnoten().size());
         ArrayList <Knoten> knotenListeX = new ArrayList<>(kService.ohneId());


         for(Knoten k : knotenListeX){
             Knoten x = new Knoten(k.getNr(),k.getName(),k.getBeschr(),k.getDauer(),k.getVorgaenger());
             knotenListe.add(x);

         }
        System.out.println(knotenListe);

         System.out.println("O H N E  ID: " + kService.ohneId());

        model.addAttribute("listKnoten",knotenListe);

        System.out.println("HIER DIE LISTE:" + knotenListe);
        //
        service.allesBerechnen();
     //   model.addAttribute("projektDauer", service.projektDauerGesamt());
        model.addAttribute("mykritischerPfad", ausgabe.ausgabeKritischerPfadBootStrap());
       // System.out.println(service.projektDauerGesamt());

      //  ausgabe.listeAusgeben(knotenListe);

        return "knoten";
    }

    @GetMapping("/knoten/neu")
    public String showAddKnotenForm(Model model){
        List<Knoten> listKnoten = kService.listAllKnoten();
        model.addAttribute("listKnoten", listKnoten);
        model.addAttribute("pageTitle","Knoten hinzufügen");
        model.addAttribute("knoten",new Knoten());
        return "knoten_neu";
    }

    @GetMapping("/knoten/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,Model model, RedirectAttributes ra ){
        try {
            Knoten knoten = kService.get(id);
            model.addAttribute("knoten",knoten);
            model.addAttribute("pageTitle","Knoten bearbeiten Nr.: " + id);
            return "knoten_edit";
        } catch (KnotenNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/knoten";
        }
    }

    @GetMapping("/knoten/delete/{id}")
    public String deleteKnoten(@PathVariable("id") Integer id, RedirectAttributes ra ){
        try {
            kService.deleteKnoten(id);
            ra.addFlashAttribute("message", "Knoten mit ID: " + id + " wurde gelöscht.");
        } catch (KnotenNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/knoten";
    }

    @PostMapping("/knoten/save")
    public String saveKnoten(Knoten knoten,@RequestParam(name = "vorgaenger1", required = false) List<Integer> knotenId , RedirectAttributes ra){

        if(knotenId == null){
            knotenId = new ArrayList<>(1);
            knotenId.add(0);
        }

        knoten.setVorgaenger(knotenId);
           kService.save(knoten);
        //    System.out.println(knotenId.get(i));
        System.out.println(knotenId);
        kService.save(knoten);
        ra.addFlashAttribute("message","Der Knoten wurde erfolgreich gespeichert");
        return "redirect:/knoten";
    }

    @PostMapping("/knoten/saveEdit")
    public String saveKnoten(Knoten knoten,RedirectAttributes ra){
        kService.save(knoten);
        ra.addFlashAttribute("message","Der Knoten wurde erfolgreich gespeichert");
        return "redirect:/knoten";
    }

    @GetMapping("/vorgaenger")
    public String showAddVorgaengerForm(Model model){
        List<Knoten> listKnoten = kService.listAllKnoten();
        model.addAttribute("listKnoten", listKnoten);
        return "vorgaenger";
    }


}
