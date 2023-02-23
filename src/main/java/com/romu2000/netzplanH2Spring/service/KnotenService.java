package com.romu2000.netzplanH2Spring.service;

import com.romu2000.netzplanH2Spring.model.Knoten;
import com.romu2000.netzplanH2Spring.repository.KnotenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KnotenService {
    @Autowired
    private KnotenRepository kRepo;


    public List<Knoten> listAllKnoten(){

        return (List<Knoten>) kRepo.findAll();
    }

    public List<Knoten> ohneId(){
        return kRepo.findOhneId();
    }

    public List<Knoten> listDistinctAllKnoten(){
       return (List<Knoten>) kRepo.findDistinctIds();
    }

    public void save(Knoten knoten) {
        kRepo.save(knoten);
    }
    public Knoten get(Integer id) throws KnotenNotFoundException {

        Optional<Knoten> result = kRepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new KnotenNotFoundException("Keine Knoten gefunden mit der ID: " + id);
    }

    public void deleteKnoten(Integer id) throws KnotenNotFoundException{
        Long count = kRepo.countByid(id);
        if(count == null || count == 0){
            throw new KnotenNotFoundException("Keine Knoten gefunden mit der ID: " + id);
        }else{
            kRepo.deleteById(id);
        }
    }



}
