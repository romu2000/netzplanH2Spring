package com.romu2000.netzplanH2Spring.repository;

import com.romu2000.netzplanH2Spring.model.Knoten;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface KnotenRepository extends CrudRepository<Knoten,Integer> {
    public Long countByid(Integer id);

}
