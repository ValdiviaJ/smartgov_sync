package com.smartgov.sync.repository;

import com.smartgov.sync.model.PersonalEspecialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalEspecialistaRepository extends JpaRepository<PersonalEspecialista, Long> {
    List<PersonalEspecialista> findByFechaActualizacionGreaterThan(String timestamp);
}
