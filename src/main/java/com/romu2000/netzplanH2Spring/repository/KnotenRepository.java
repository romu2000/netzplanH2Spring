package com.romu2000.netzplanH2Spring.repository;

import com.romu2000.netzplanH2Spring.model.Knoten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface KnotenRepository extends JpaRepository<Knoten,Integer> {
    public Long countByid(Integer id);
    @Query("SELECT DISTINCT k FROM knoten k")
    public List<Knoten> findDistinctIds();

    @Query("SELECT k from knoten k")
    public List<Knoten> ohneId();

    List<Knoten> findOhneId();

}