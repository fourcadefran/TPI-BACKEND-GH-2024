package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Posicion;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.services.PosicionService;
import fran.fourcade.pruebasmicroservice.services.VehiculoService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {

    private final PosicionService posicionService;
    private final VehiculoService vehiculoService;

    @Autowired
    public PosicionController(PosicionService posicionService, VehiculoService vehiculoService) {
        this.posicionService = posicionService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Posicion>> getAllPosiciones() {
        return ResponseEntity.ok(posicionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posicion> getPosicionById(@PathVariable Long id) {
        try {
            Posicion posicion = posicionService.getById(id);
            return ResponseEntity.ok(posicion);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Posicion> createPosicion(@RequestBody Posicion posicion) {
        try {
            // Verifica si el vehículo existe antes de crear la posición
            Vehiculo vehiculo = vehiculoService.getById(posicion.getVehiculo().getId());
            posicion.setVehiculo(vehiculo);
            return ResponseEntity.ok(posicionService.create(posicion));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.badRequest()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Posicion> updatePosicion(@PathVariable Long id, @RequestBody Posicion posicionDetails) {
        try {
            Posicion posicion = posicionService.getById(id);
            posicion.setFechaHora(posicionDetails.getFechaHora());
            posicion.setLatitud(posicionDetails.getLatitud());
            posicion.setLongitud(posicionDetails.getLongitud());

            // Verifica si el vehículo existe antes de asignarlo a la posición
            Vehiculo vehiculo = vehiculoService.getById(posicionDetails.getVehiculo().getId());
            posicion.setVehiculo(vehiculo);

            return ResponseEntity.ok(posicionService.update(id, posicion));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Posicion> deletePosicion(@PathVariable Long id) {
        try {
            Posicion posicion = posicionService.getById(id);
            posicionService.delete(id);
            return ResponseEntity.ok(posicion);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
