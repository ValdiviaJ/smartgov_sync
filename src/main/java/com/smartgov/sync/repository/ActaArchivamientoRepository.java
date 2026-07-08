package com.smartgov.sync.repository;

import com.smartgov.sync.model.ActaArchivamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActaArchivamientoRepository extends JpaRepository<ActaArchivamiento, Long> {
    List<ActaArchivamiento> findByFechaActualizacionGreaterThan(String timestamp);
}
