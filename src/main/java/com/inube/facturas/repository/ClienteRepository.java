package com.inube.facturas.repository;

import com.inube.facturas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository indica que esta interfaz es un componente de acceso a datos (DPA)
// Spring detecta automáticamente esta capa y la convierte en un bean para inyectarlo

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Consulta personalizada usando JPQL.
    // LEFT JOIN FETCH: carga al cliente y sus teléfonos en una sola consulta (evita LazyInicializationException)
    //
    // Optional <Cliente>: devuelve un cliente envuelto en Optional para manejar el caso de "no encontrado".
    @Query("""
           SELECT c
               FROM Cliente c
               LEFT JOIN FETCH c.telefonos
                   WHERE c.idCliente = :id
    """)
    Optional<Cliente> findByIdWithPhones(Long id);

    // Igual que arriba: genera la consulta automáticamente.
    // SELECT * FROM CLIENTES WHERE ACTIVO = ?
    List<Cliente> findByActivo(Integer activo);
}
