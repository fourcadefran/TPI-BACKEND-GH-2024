package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.dtos.FinalizarPruebaDTO;
import fran.fourcade.pruebasmicroservice.dtos.PruebaDTO;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.services.PruebasService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: CREAR DTOS

@Slf4j
@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {
    private final PruebasService pruebasService;

    @Autowired
    public PruebaController(PruebasService pruebasService) {
        this.pruebasService = pruebasService;
    }


    @GetMapping
    public ResponseEntity<Iterable<Prueba>> getAllPruebas() {
        return ResponseEntity.ok(pruebasService.getAll());
    }

    // 1 - B
    @GetMapping("/now")
    public ResponseEntity<Iterable<Prueba>> getAllPruebasNow() {
        return ResponseEntity.ok(pruebasService.getAllNow());
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

    // 1 - A
    @PostMapping
    public ResponseEntity<Prueba> createPrueba(@RequestBody PruebaDTO request) {
        return ResponseEntity.ok(pruebasService.create(request));
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
    // 1 - C
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Prueba> finalizarPrueba(@PathVariable Long id, @RequestBody FinalizarPruebaDTO request) {
        try {
            // todo: crear dto requestComentario pasar solo comentario
            return ResponseEntity.ok(pruebasService.finalizar(id, request.getComentarios()));
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
