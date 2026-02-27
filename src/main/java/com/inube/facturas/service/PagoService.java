package com.inube.facturas.service;

import com.inube.facturas.model.Pago;
import com.inube.facturas.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public BigDecimal totalPagadoFactura(Long idFactura) {
        return pagoRepository.sumarPagosPorFactura(idFactura);
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }
}