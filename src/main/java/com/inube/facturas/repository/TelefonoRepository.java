package com.inube.facturas.repository;
import com.inube.facturas.model.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono,Long>{

    //Puedes agregar métodos de búsqueda personalizados aquí, por ejemplo:
    List<Telefono> findByClienteIdCliente(Long idCliente);
}
