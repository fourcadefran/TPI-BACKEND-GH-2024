package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.services.InteresadoService;
import fran.fourcade.pruebasmicroservice.services.PruebasService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/interesados")
public class InteresadoController {
    private final InteresadoService interesadoService;
    private final PruebasService pruebasService;

    @Autowired
    public InteresadoController(InteresadoService interesadoService, PruebasService pruebasService) {
        this.interesadoService = interesadoService;
        this.pruebasService = pruebasService;
    }
    @GetMapping
    public ResponseEntity<Iterable<Interesado>> getAllInteresados() {
        return ResponseEntity.ok(interesadoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interesado> getInteresadoById(@PathVariable Long id) {
        try {
            Interesado interesado = interesadoService.getById(id);
            return ResponseEntity.ok(interesado);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Interesado> createInteresado(@RequestBody Interesado interesado) {
        return ResponseEntity.ok(interesadoService.create(interesado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interesado> updateInteresado(@PathVariable Long id, @RequestBody Interesado interesadoDetails) {
        try {
            Interesado interesado = interesadoService.getById(id);
            interesado.setTipoDocumento(interesadoDetails.getTipoDocumento());
            interesado.setDocumento(interesadoDetails.getDocumento());
            interesado.setNombre(interesadoDetails.getNombre());
            interesado.setApellido(interesadoDetails.getApellido());
            interesado.setRestringido(interesadoDetails.getRestringido());
            interesado.setNroLicencia(interesadoDetails.getNroLicencia());
            interesado.setFechaVencimientoLicencia(interesadoDetails.getFechaVencimientoLicencia());

            Prueba prueba = pruebasService.getById(interesadoDetails.getPrueba().getId());
            interesado.setPrueba(prueba);
            return ResponseEntity.ok(interesadoService.update(id, interesado));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Interesado> deleteInteresado(@PathVariable Long id) {
        try {
            Interesado interesado = interesadoService.getById(id);
            interesadoService.delete(id);
            return ResponseEntity.ok(interesado);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}