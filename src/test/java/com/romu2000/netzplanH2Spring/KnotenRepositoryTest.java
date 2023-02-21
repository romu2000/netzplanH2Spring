package com.romu2000.netzplanH2Spring;

import com.romu2000.netzplanH2Spring.model.Knoten;
import com.romu2000.netzplanH2Spring.repository.KnotenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class KnotenRepositoryTest {
    @Autowired private KnotenRepository repo;

    @Test
    public void testAddNew(){
        Knoten knoten = new Knoten();
        knoten.setBeschr("Beschreibung");
        knoten.setDauer(2);
        knoten.setName("Knoten Name");
        knoten.setNr(1);

        Knoten savedKnoten = repo.save(knoten);
    }



    @Test
    public void testListAll(){
        Iterable<Knoten> knoten = repo.findAll();

        for (Knoten knoten1 : knoten) {
            System.out.println(knoten1);
        }
    }

    @Test
    public void testUpdateKnoten(){
        Integer knotenId = 1;
        Optional<Knoten> optionalKnoten = repo.findById(knotenId);

        Knoten knoten = optionalKnoten.get();
        knoten.setName("Hans");
        repo.save(knoten);

        Knoten updatedKnoten = repo.findById(knotenId).get();
    }
}
