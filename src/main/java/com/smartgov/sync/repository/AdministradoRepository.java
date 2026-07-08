package com.smartgov.sync.repository;

import com.smartgov.sync.model.Administrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministradoRepository extends JpaRepository<Administrado, Long> {
    List<Administrado> findByFechaActualizacionGreaterThan(String timestamp);
}
