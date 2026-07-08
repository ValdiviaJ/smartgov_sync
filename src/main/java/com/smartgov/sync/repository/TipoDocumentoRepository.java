package com.smartgov.sync.repository;

import com.smartgov.sync.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    List<TipoDocumento> findByFechaActualizacionGreaterThan(String timestamp);
}
