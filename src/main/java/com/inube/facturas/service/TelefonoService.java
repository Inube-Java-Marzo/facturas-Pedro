package com.inube.facturas.service;

import com.inube.facturas.model.Telefono;
import com.inube.facturas.repository.TelefonoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {

    private TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository telefonoRepository) { this.telefonoRepository = telefonoRepository; }

    public List<Telefono> findAll() {return telefonoRepository.findAll();}

    public Optional<Telefono> findById(Long id) {return telefonoRepository.findById(id);}

    // Metodo para guardar un nuevo telefono
    public Telefono save(Telefono telefono) {
        //Logica: Podrias validad el formato de telefono antes de guardar
        return telefonoRepository.save(telefono);
    }

    public void deleteById(Long id) {telefonoRepository.deleteById(id);}
}

