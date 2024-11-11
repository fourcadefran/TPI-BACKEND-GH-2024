package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.services.PruebasService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/pruebas") // las demás entidades van a tener /api/pruebas/vehiculos
public class PruebaController {
    // todo: hacer los endpoints de las demas entidades con esta base
    // ejemplo : /api/pruebas/vehiculos/{id}
    private final PruebasService pruebasService;

    @Autowired
    public PruebaController(PruebasService pruebasService) {
        this.pruebasService = pruebasService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Prueba>> getAllPruebas() {
        return ResponseEntity.ok(pruebasService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prueba> getPruebaById(@PathVariable Long id) {
        try {
            Prueba prueba = pruebasService.getById(id);
            return ResponseEntity.ok(prueba);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Prueba> createPrueba(@RequestBody Prueba prueba) {
        return ResponseEntity.ok(pruebasService.create(prueba));
    }
    //todo: create generic put method


    @PutMapping("/{id}")
    public ResponseEntity<Prueba> updatePrueba(@PathVariable Long id, @RequestBody Prueba pruebaDetails) {
        try {
            return ResponseEntity.ok(pruebasService.update(id, pruebaDetails));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
    //todo: replicar esto en todos los endpoints / entidades
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Prueba> finalizarPrueba(@PathVariable Long id, @RequestBody Prueba pruebaDetails) {
        try {
            // todo: crear dto requestComentario pasar solo comentario
            return ResponseEntity.ok(pruebasService.finalizar(id, pruebaDetails.getComentarios()));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Prueba> deletePrueba(@PathVariable Long id) {
        try {
            Prueba prueba = pruebasService.getById(id);
            pruebasService.delete(id);
            return ResponseEntity.ok(prueba);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

}
