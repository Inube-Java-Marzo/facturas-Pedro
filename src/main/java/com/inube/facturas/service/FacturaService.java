package com.inube.facturas.service;

import com.inube.facturas.model.Factura;
import com.inube.facturas.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
//Buena práctica: hacer el constructor por inyector
    //@Autowired (inyecciones) no siempre esta configurado en servidor
    private final FacturaRepository facturaRepository; //Creacion del bean

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;//Inyeccion del bean
    }

    public List<Factura>  findAll() {return facturaRepository.findAll();}
    public List<Factura> findByActivo() {return facturaRepository.findByActivo(1);}
    public List<Factura> findByInActivo() {return facturaRepository.findByActivo(0);}

    public Optional<Factura> findById(Long id) {return facturaRepository.findById(id);}

    public Optional<Factura> findByIdWithPagos(Long id){
     return facturaRepository.findByIdWithPagos(id);
    }

    //Guardar factura
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    //Inactivar un registro
    public void inactivar(Long id){
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("ID invalido: "+ id));

        //Settear el campo ACTIVO a 0
        factura.setActivo(0);
        facturaRepository.save(factura);
    }

    //Inactivar un registro
    public void activar(Long id){
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("ID invalido: "+ id));

        //Settear el campo ACTIVO de 0 a 1
        factura.setActivo(1);
        facturaRepository.save(factura);
    }
}
