package com.inube.facturas.service;

import com.inube.facturas.model.Cliente;
import com.inube.facturas.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service indica que esta clase pertenece a la capa servicio.
// Aqui se implementa la logica del negocio (reglas, validaciones, flujos, conversiones)
@Service
public class ClienteService {

    //Dependencia del repositorio que maneja la BD
    private final ClienteRepository clienteRepository;

    // Inyeccion de dependencias por constructor (buena práctica)
    // Spring inyecta automaticamente un ClienteRepository cuando crea el servicio.
    public ClienteService(ClienteRepository clienteRepository) {this.clienteRepository = clienteRepository;}

    // Obtiene todos los clientes de la base de datos
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Regresa unicamente clientes activos (ACTIVO = 1)
    public List<Cliente> findActivo() {
        return clienteRepository.findByActivo(1);
    }

    // Regresa unicamente clientes inactivos (ACTIVO = 0)
    public List<Cliente> findInActivo() {
        return clienteRepository.findByActivo(0);
    }

    // Busca un cliente por ID usando el metodo basico de JpaRepository.
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    //Igual que findById, pero cargando también la lista de telefonos
    public Optional<Cliente> findByIdWithPhones(Long id) {
        return clienteRepository.findByIdWithPhones(id);
    }

    // Guarda o actualiza un cliente.
    // Si el ID es null -> crea uno nuevo.
    // Si el ID existe -> actualiza el registro.
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // En lugar de borrar registros fisicamente, se desactiva el cliente.
    // Esto es una buena práctica llamada "borrado logico"
    public void inactivar(Long id) {
        //Busca el cliente; si no lo encuentra, lanza excepcion.
        Cliente cliente = clienteRepository.findById(id) //Creacion de un objeto cliente
                .orElseThrow(() -> new IllegalArgumentException("ID invalido: "+ id));

        //Cambia el estado a inactivo.
        cliente.setActivo(0);

        //Guarda el cambio en la BD. (A traves del Repositorio)
        clienteRepository.save(cliente); //Save de JPA no del servicio
    }

    // Activa un cliente previamente desactivado.
    public void activar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalido: "+ id));
        cliente.setActivo(1);
        clienteRepository.save(cliente);
    }
}
