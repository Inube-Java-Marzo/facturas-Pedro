package com.inube.facturas.MvcController;

import com.inube.facturas.model.Cliente;
import com.inube.facturas.model.Factura;
import com.inube.facturas.model.Pago;
import com.inube.facturas.service.ClienteService;
import com.inube.facturas.service.FacturaService;
import com.inube.facturas.service.PagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //Necesario para el User Interface
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller

@RequestMapping ("/web/facturas")
public class FacturaMvcController {
    //Inyectar el service para que lo consuma
    private final FacturaService facturaService;
    private final ClienteService clienteService;
    private final PagoService pagoService;

    public FacturaMvcController(FacturaService facturaService, ClienteService clienteService, PagoService pagoService) {
         this.facturaService = facturaService;
         this.clienteService = clienteService;
         this.pagoService = pagoService;
    }

    // ===================================
    //         LISTADO DE FACTURAS
    // ===================================

    @GetMapping
    public String listaFacturas(Model model) {
        model.addAttribute("facturas", facturaService.findAll());
        model.addAttribute("filtro", "todos"); //Buena practica para debuggear, solo son indicadores
        return "facturas/list";
    }

    // ACTIVOS
    @GetMapping ("/activos")
    public String activos(Model model) {
        model.addAttribute("facturas", facturaService.findByActivo());
        model.addAttribute("filtro", "activos");
        return "facturas/list";
    }

    //INACTIVOS
    @GetMapping ("/inactivos")
    public String inactivos(Model model) {
        model.addAttribute("facturas", facturaService.findByInActivo());
        model.addAttribute("filtro", "inactivos");
        return "facturas/list";
    }

    // ===================================
    //    FORMULARIO DE NUEVO / EDITAR
    // ===================================

    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioFactura(@PathVariable(required = false) Long id, Model model) {

        Factura factura;

        if (id != null) {
            factura = facturaService.findById(id)
                    .orElseThrow(() -> new RuntimeException("ID invalido: " + id));
            model.addAttribute("action", "edit");
        } else {
            factura = new Factura();
            factura.setActivo(1);
            factura.setCliente(new Cliente());//Setter de cliente en blanco
            model.addAttribute("action", "new");
        }
        model.addAttribute("factura", factura);
        model.addAttribute("clientes", clienteService.findAll());
        return "facturas/form";
    }
        // ===================================
        //   GUARDAR FACTURA (CREATE/UPDATE)
        // ===================================

        @PostMapping
        public String saveFactura(@ModelAttribute Factura factura){
            if (factura.getFechaFactura() == null) {
                factura.setFechaFactura(LocalDate.now());
            }
            if (factura.getAnio() == null || factura.getAnio().isBlank()) {
                factura.setAnio(String.valueOf(LocalDate.now().getYear()));
            }
            if (factura.getActivo() == null) {
                factura.setActivo(1);
            }
            facturaService.save(factura);
            return "redirect:/web/facturas";
        }

    // ===================================
    //       DETALLES DE UNA FACTURA
    // ===================================
    @GetMapping("/{id}")
    public String showDetalleFactura(@PathVariable Long id, Model model) {
        Factura factura = facturaService.findByIdWithPagos(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));
        BigDecimal totalPagado = pagoService.totalPagadoFactura(id);

        // compareTo evita fallos por escala (100.0 vs 100.00)
        if (factura.getMonto() != null && totalPagado != null && totalPagado.compareTo(factura.getMonto()) >= 0) {
            if (!Integer.valueOf(0).equals(factura.getActivo())) {
                factura.setActivo(0);
                facturaService.save(factura);
            }
        } else if (!Integer.valueOf(1).equals(factura.getActivo())) {
            factura.setActivo(1);
            facturaService.save(factura);
        }

        model.addAttribute("factura", factura);
        model.addAttribute("totalPagado", totalPagado);
        return "facturas/show";
    }//verificar el de clientes

    // ===================================
    //      ACTIVAR / DESACTIVAR CLIENTE
    // ===================================

    @GetMapping("/inactivar/{id}")
    public String inactivarFactura(@PathVariable Long id) {
        facturaService.inactivar(id);
        return "redirect:/web/facturas";
    }

    @GetMapping("/activar/{id}")
    public String activarFactura(@PathVariable Long id) {
        facturaService.activar(id);
        return "redirect:/web/facturas";
    }

}
