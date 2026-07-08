package com.smartgov.sync.repository;

import com.smartgov.sync.model.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    List<Oficina> findByFechaActualizacionGreaterThan(String timestamp);
}
