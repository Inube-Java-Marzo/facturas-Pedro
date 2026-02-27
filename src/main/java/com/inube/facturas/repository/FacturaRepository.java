package com.inube.facturas.repository;

import com.inube.facturas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//Parametros JPA : Clase y llave primaria
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
//FETCH paa evitar excepcion de tipo LAZY inicialization exception.

    @Query("""
        SELECT f
          FROM Factura f
          LEFT JOIN FETCH f.pagos
                  WHERE f.idFactura = :id               
        """)

    Optional<Factura> findByIdWithPagos(@Param("id") Long id);

    List<Factura> findByActivo(Integer activo);

}
