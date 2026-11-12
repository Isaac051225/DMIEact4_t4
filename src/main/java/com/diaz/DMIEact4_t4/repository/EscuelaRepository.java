package com.diaz.DMIEact4_t4.repository;

import com.diaz.DMIEact4_t4.Entity.Escuela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, Long> {
    boolean existsByClave(String clave);
}