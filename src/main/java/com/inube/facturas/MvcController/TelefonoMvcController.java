package com.inube.facturas.MvcController;

import com.inube.facturas.model.Telefono;
import com.inube.facturas.repository.TelefonoRepository;
import com.inube.facturas.service.ClienteService;
import com.inube.facturas.service.TelefonoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;//Muy importante para addAttribute, no confundir, core.model.Model
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/telefonos")
public class TelefonoMvcController {

    private TelefonoService telefonoService;
    private ClienteService clienteService; //Para poblar la lista de clientes en el listado

    public TelefonoMvcController(TelefonoService telefonoService, ClienteService clienteService) {
        this.telefonoService = telefonoService;
        this.clienteService = clienteService;
    }

    //1. Listar todos
    @GetMapping
    public String listarTelefonos(Model model) {
        List<Telefono> telefonos = telefonoService.findAll();
        model.addAttribute("telefonos", telefonos);
        return "telefonos/list";
    }

    // 2. Mostrar formulario de Creacion/Edicion
    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioTelefono(@PathVariable(required = false) Long id, Model model) { //Para busquedas
        Telefono telefono = (id != null)
                ? telefonoService.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de telefono invalido: "+ id))
                : new Telefono();
        model.addAttribute("telefono", telefono);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("action", id != null ? "edit" : "new");
        return "telefonos/form";
    }

    //3. Procesar Guardar
    @PostMapping
    public String saveTelefono(@ModelAttribute Telefono telefono) { //Para ediciones
        telefonoService.save(telefono);
        return "redirect:/web/telefonos";
    }

    // 4. Mostrar detalles (Opcional, si no se usa la lista para ver detalles)
    public String showDetallesTelefono(@PathVariable Long id, Model model) {
        Telefono telefono = telefonoService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de telefono invalido: "+ id));
        model.addAttribute("telefono", telefono);
        return "telefonos/details";
    }

    //5. Eliminar
    @GetMapping("/delete/{id}")
    public String deleteTelefono(@PathVariable Long id, Model model) {
        telefonoService.deleteById(id);
        return "redirect:/web/telefonos";
    }

}
