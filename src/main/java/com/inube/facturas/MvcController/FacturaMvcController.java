package com.inube.facturas.MvcController;

import com.inube.facturas.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //Necesario para el User Interface
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping ("/web/facturas")
public class FacturaMvcController {
    //Inyectar el service para que lo consuma
    private final FacturaService facturaService;

    public FacturaMvcController(FacturaService facturaService, FacturaService facturaService1) {
         this.facturaService = facturaService;
    }

    @GetMapping
    public String listaFacturas(Model model) {
        model.addAttribute("facturas", facturaService.findAll());
        model.addAttribute("filtro", "todos");
        return "facturas/list";
    }
}
