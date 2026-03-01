package com.inube.facturas.MvcController;

import com.inube.facturas.model.Cliente;
import com.inube.facturas.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller indica que esta clase pertenece a la capa web/MVC.
// Se usa para devolver vistas (HTML) con ModelAndView o Model.
//@Controller Sirve también para que spring lo reconozca como capa de controlador
@Controller

// Prefijo comun para todas las rutas del controlador.
// Ejemplo: /web/clientes, /web/clientes/activos, etc.

@RequestMapping("web/clientes")
public class ClienteMvcController {

    // Inyección del servicio donde está la lógica de negocio
    //Llamado Bean (como semilla de inicio), es la clase del repositorio, solo viven dentro de esta clase
    private final ClienteService clienteService;

    // Inyeccion por constructor (recomendada)
    public ClienteMvcController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ===================================
    //         LISTADO DE CLIENTES
    // ===================================

    // GET /web/clientes
    // Lista todos los clientes

    @GetMapping
    public String listaClientes(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("filtro", "todos");
        return "clientes/list";
    }

    // GET /web/clientes/activos
    @GetMapping("/activos")
    public String activos(Model model) {
        model.addAttribute("clientes", clienteService.findActivo());
        model.addAttribute("filtro", "activos");
        return "clientes/list";
    }

    // GET /web/clientes/inactivos
    @GetMapping("/inactivos")
    public String inactivos(Model model) {
        model.addAttribute("clientes", clienteService.findInActivo());
        model.addAttribute("filtro", "inactivos");
        return "clientes/list";
    }

    // ===================================
    //    FORMULARIO DE NUEVO / EDITAR
    // ===================================

    // GET /web/clientes/new          -> nuevo cliente
    // get /web/clientes/edit/{id}    -> editar cliente existente
    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioCliente(@PathVariable(required = false) Long id, Model model) {

        Cliente cliente;

        if (id != null) {
            // Si viene ID -> editar
            cliente = clienteService.findById(id)
                    .orElseThrow(() -> new RuntimeException("ID invalido: " + id));
            model.addAttribute("action", "edit");
        } else {
            // Si viene ID -> crear nuevo
            cliente = new Cliente();
            cliente.setActivo(1);
            //Por defecto activo
            model.addAttribute("action", "new");
        }
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    // ===================================
    //   GUARDAR CLIENTE (CREATE/UPDATE)
    // ===================================

    // POST /web/clientes
    // Sprig llena automaticamente el objeto Cliente con los valores del formulario.

    @PostMapping
    public String saveCliente(@ModelAttribute Cliente cliente) {
        clienteService.save(cliente);
        // Después de guardar redirige el listado principal.
        return "redirect:/web/clientes";
    }

    // ===================================
    //       DETALLES DE UN CLIENTE
    // ===================================

    // GET /web/clientes/{id}
    @GetMapping("/{id}")
    public String showDetallesCliente(@PathVariable Long id, Model model) {

        // Usa el metood que carga también los telefonos (JOIN FETCH)
        Cliente cliente = clienteService.findByIdWithPhones(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        model.addAttribute("cliente", cliente);
        return "clientes/show";
    }
    // ===================================
    //      ACTIVAR / DESACTIVAR CLIENTE
    // ===================================

    // GET /web/clientes/inactivar/{id}
    @GetMapping("/inactivar/{id}")
    public String inactivarCliente(@PathVariable Long id, Model model) {
        clienteService.inactivar(id);
        return "redirect:/web/clientes";
    }

    // GET /web/clientes/activar/{id}
    @GetMapping("/activar/{id}")
    public String activarCliente(@PathVariable Long id, Model model) {
        clienteService.activar(id);
        return "redirect:/web/clientes";
    }

}
