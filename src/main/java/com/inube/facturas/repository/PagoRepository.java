package com.inube.facturas.repository;

import com.inube.facturas.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
     //@Query Es una anotación de Spring Data JPA que permite escribir una consulta JPQL manual en
    // lugar de usar los métodos automáticos del repositorio.
    @Query("""
    SELECT COALESCE(SUM(p.monto), 0)
    FROM Pago p
    WHERE p.factura.idFactura = :idFactura
""")
    //FROM Pago p → Trabaja con la entidad Pago.
    //
    //SUM(p.monto) → Suma todos los valores del campo monto.
    //
    //WHERE p.factura.idFactura = :idFactura → Solo suma los pagos que pertenecen a una factura específica.
    //
    //COALESCE(..., 0) → Si no existen pagos (la suma es null), devuelve 0 en lugar de null.

    BigDecimal sumarPagosPorFactura(@Param("idFactura") Long idFactura);
}
