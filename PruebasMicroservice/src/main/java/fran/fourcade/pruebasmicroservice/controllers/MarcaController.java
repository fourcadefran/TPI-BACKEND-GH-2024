package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Marca;
import fran.fourcade.pruebasmicroservice.services.MarcaService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    @Autowired
    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Marca>> getAllMarcas() {
        return ResponseEntity.ok(marcaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        try {
            Marca marca = marcaService.getById(id);
            return ResponseEntity.ok(marca);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.create(marca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable Long id, @RequestBody Marca marcaDetails) {
        try {
            Marca marca = marcaService.getById(id);
            marca.setNombre(marcaDetails.getNombre());
            return ResponseEntity.ok(marcaService.update(id, marca));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Marca> deleteMarca(@PathVariable Long id) {
        try {
            Marca marca = marcaService.getById(id);
            marcaService.delete(id);
            return ResponseEntity.ok(marca);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
