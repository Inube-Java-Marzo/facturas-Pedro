package com.inube.facturas.MvcController;

import com.inube.facturas.model.Pago;
import com.inube.facturas.service.FacturaService;
import com.inube.facturas.service.PagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web/pagos")
public class PagoMvcController {
    //Inyectar el service
    private final PagoService pagoService;
    private final FacturaService facturaService;

    public PagoMvcController(PagoService pagoService, FacturaService facturaService) {
        this.pagoService = pagoService;
        this.facturaService = facturaService;
    }

    // ===================================
    //         LISTADO DE FACTURAS
    // ===================================

    @GetMapping String listarPagos(Model model) {
        model.addAttribute("pagos", pagoService.findAll());
        return "pagos/list";
    }

    // ===================================
    //    FORMULARIO DE NUEVO / EDITAR
    // ===================================

    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioPago(@PathVariable(required = false) Long id, Model model){
        Pago pago = (id != null)
                ? pagoService.findById(id) .orElseThrow(() -> new IllegalArgumentException("ID de pago invalido: "+ id))
                : new Pago();
        model.addAttribute("pago", pago);
        model.addAttribute("facturas", facturaService.findAll());
        model.addAttribute("action", id != null ? "edit" : "new");
        return "pagos/form";
    }

    // ===================================
    //            GUARDAR PAGO
    // ===================================

    @PostMapping
    public String savePago(@ModelAttribute("pago") Pago pago){
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDate.now());
        }
        pagoService.save(pago);
        return "redirect:/web/pagos";
    }
}
