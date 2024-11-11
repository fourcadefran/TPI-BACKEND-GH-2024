package fran.fourcade.empleadosmicroservice.controllers;

import fran.fourcade.empleadosmicroservice.models.Empleado;
import fran.fourcade.empleadosmicroservice.services.EmpleadoService;
import fran.fourcade.empleadosmicroservice.services.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Empleado>> getAllEmpleados() {
        return ResponseEntity.ok(empleadoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.getById(id);
            return ResponseEntity.ok(empleado);
        } catch (ServiceException e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Empleado>> getEmpleadosByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(empleadoService.getByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.create(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoDetails) {
        try {
            Empleado empleado = empleadoService.getById(id);
            empleado.setNombre(empleadoDetails.getNombre());
            empleado.setApellido(empleadoDetails.getApellido());
            empleado.setTelefonoContacto(empleadoDetails.getTelefonoContacto());
            return ResponseEntity.ok(empleadoService.update(id, empleado));
        } catch (ServiceException e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.getById(id);
            empleadoService.delete(id);
            return ResponseEntity.ok(empleado);
        } catch (ServiceException e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
