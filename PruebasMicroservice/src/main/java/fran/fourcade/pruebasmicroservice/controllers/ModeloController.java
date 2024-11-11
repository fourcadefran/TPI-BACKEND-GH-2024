package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Modelo;
import fran.fourcade.pruebasmicroservice.models.Marca;
import fran.fourcade.pruebasmicroservice.services.ModeloService;
import fran.fourcade.pruebasmicroservice.services.MarcaService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;
    private final MarcaService marcaService;

    @Autowired
    public ModeloController(ModeloService modeloService, MarcaService marcaService) {
        this.modeloService = modeloService;
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Modelo>> getAllModelos() {
        return ResponseEntity.ok(modeloService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModeloById(@PathVariable Long id) {
        try {
            Modelo modelo = modeloService.getById(id);
            return ResponseEntity.ok(modelo);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Modelo> createModelo(@RequestBody Modelo modelo) {
        try {
            // Verifica si la marca existe antes de crear el modelo
            Marca marca = marcaService.getById(modelo.getMarca().getId());
            modelo.setMarca(marca);
            return ResponseEntity.ok(modeloService.create(modelo));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.badRequest()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> updateModelo(@PathVariable Long id, @RequestBody Modelo modeloDetails) {
        try {
            Modelo modelo = modeloService.getById(id);
            modelo.setDescripcion(modeloDetails.getDescripcion());

            // Verifica si la marca existe antes de asignarla al modelo
            Marca marca = marcaService.getById(modeloDetails.getMarca().getId());
            modelo.setMarca(marca);

            return ResponseEntity.ok(modeloService.update(id, modelo));
        } catch (Exception | ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Modelo> deleteModelo(@PathVariable Long id) {
        try {
            Modelo modelo = modeloService.getById(id);
            modeloService.delete(id);
            return ResponseEntity.ok(modelo);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
